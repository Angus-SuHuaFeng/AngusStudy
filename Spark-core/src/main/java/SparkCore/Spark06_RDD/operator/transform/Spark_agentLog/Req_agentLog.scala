package SparkCore.Spark06_RDD.operator.transform.Spark_agentLog

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Req_agentLog {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Req_agentLog")
    val sc = new SparkContext(sparkConf)

//    TODO 案例实操

//    1.获取原始数据: 时间戳，省份，城市，用户，广告
    val dateRDD: RDD[String] = sc.textFile("Spark-core/datas/agent.log")

//    2.将原始数据进行结构转换，方便统计
//      时间戳，省份，城市，用户，广告 => （（省份，广告），1 ）
    val mapRDD: RDD[((String, String), Int)] = dateRDD.map(
      line => {
        val data: Array[String] = line.split(" ")
        ((data(1), data(4)), 1)
      }
    )

//    3.将转换后的数据进行分组聚合
//     ((省份, 广告), 1 ) => ((省份, 广告), sum)
    val reduceRDD: RDD[((String, String), Int)] = mapRDD.reduceByKey(_ + _)
//    4.将聚合结果进行结构转换
//     ((省份, 广告), sum) => (省份, (广告, sum))
    val newMapRDD: RDD[(String, (String, Int))] = reduceRDD.map {
      case ((prv, ad), sum) => {
        (prv, (ad, sum))
      }
    }

//    5.将转换后的数据根据省份进行分组
//     (省份, (广告, sum)) => (省份, ((广告A, sum),(广告B, sum),(广告C, sum)...))
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = newMapRDD.groupByKey()

//    6.将分组后的数据进行组内降序排序并取前三
    val resultRDD: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(
      iter => {
        iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(3)
      }
    )

//    7.打印在控制台
    resultRDD.collect().foreach(println)

    sc.stop()
  }
}
