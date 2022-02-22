package SparkSql.SparlSql_Req

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{DataFrame, Encoder, Encoders, SparkSession, functions}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Req_Top3 extends App {
  System.setProperty("HADOOP_USER_NAME", "root")
  //TODO 创建SparkSql的运行环境
  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Req_Top3")
  private val spark: SparkSession = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()

  //  TODO 使用SparkSql连接外置的Hive
  spark.sql("use angus")


//  查询基本数据
  spark.sql(
    """
      |select
      |					a.*,
      |					p.product_name,
      |					c.area,
      |					c.city_name
      |				from user_visit_action a
      |				join product_info p on a.click_product_id = p.product_id
      |				join city_info c on a.city_id = c.city_id
      |				where a.click_product_id > -1
      |""".stripMargin
  ).createOrReplaceTempView("t1")

//  根据区域，商品进行聚合
  spark.udf.register("cityRemark", functions.udaf(new cityRemark()))
  spark.sql(
    """
      |select
      |   area,
      |   product_name,
      |   count(*) as clickCnt,
      |   cityRemark(city_name) as cityRemark
      |from t1 group by area, product_name
      |""".stripMargin
  ).createOrReplaceTempView("t2")
//  将数据按照area分区并按照click_Count排序
  spark.sql(
    """
      |select
      |			*,
      |			rank() over(partition by area order by clickCnt desc) as rank
      |from t2
      |""".stripMargin
  ).createOrReplaceTempView("t3")
//  取排序前三
    private val df: DataFrame = spark.sql(
      """
        |select *
        |from t3 where rank <= 3
        |""".stripMargin
    )
    df.show(false)
    df.write.save("Spark/output")

  //  TODO 关闭环境
  spark.close()

  /**
   * 自定义聚合函数: 实现城市备注功能
   * 1. 继承Aggregator
   * 2. 泛型
   *    IN：   城市名称
   *    BUFF： 【总点击数， Map[ (city1, cnt1, (city2, cnt2) ......]】
   *    OUT： 备注信息
   */
  case class Buff(var total: Long, var cityMap: mutable.Map[String,Long])
  class cityRemark extends Aggregator[String,Buff,String]{
//    初始化缓冲区Buff
    override def zero: Buff = {
      Buff(0, mutable.Map[String,Long]())
    }
//    更新缓冲区数据
    override def reduce(buff: Buff, city: String): Buff = {
      buff.total += 1
      val newCount: Long = buff.cityMap.getOrElse(city,0L) + 1
      buff.cityMap.update(city,newCount)
      buff
    }
//    合并
    override def merge(buff1: Buff, buff2: Buff): Buff = {
      buff1.total += buff2.total
//      val map1 = buff1.cityMap
//      val map2 = buff2.cityMap
//
//      buff1.cityMap = map1.foldLeft(map2){
//        case (map, (city,cnt)) => {
//          val newCount = map.getOrElse(city,0L) + cnt
//          map.updated(city,newCount)
//        }
//          map
//      }
      buff2.cityMap.foreach{
        case (city,cnt) => {
          val newCount: Long = buff1.cityMap.getOrElse(city, 0L) + cnt
          buff1.cityMap.update(city,newCount)
        }
      }
      buff1
    }
//    结果计算,将统计的结果生成字符串信息
    override def finish(buff: Buff): String = {
      val remarkList: ListBuffer[String] = ListBuffer[String]()
      val total: Long = buff.total
      val cityMap: mutable.Map[String, Long] = buff.cityMap
      val cityCntList: List[(String, Long)] = cityMap.toList.sortWith(
        (left, right) => {
          left._2 > right._2
        }
      ).take(2)
      val hasMore = cityMap.size>2
      var hasCount = 0L
      cityCntList.foreach{
        case (city, cnt) => {
          hasCount += cnt
          remarkList.append(city + ((cnt.toDouble/total)*100).formatted("%.2f") + "%")
        }
      }
      if (hasMore){
        remarkList.append("其他" + (((total - hasCount).toDouble/total)*100).formatted("%.2f") + "%")
      }
      remarkList.mkString(",")
    }

    override def bufferEncoder: Encoder[Buff] = Encoders.product

    override def outputEncoder: Encoder[String] = Encoders.STRING
  }
}
