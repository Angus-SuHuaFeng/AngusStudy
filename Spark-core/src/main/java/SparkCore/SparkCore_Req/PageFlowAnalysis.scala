package SparkCore.SparkCore_Req

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object PageFlowAnalysis {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("PageFlowAnalysis2"))

    val actionRDD: RDD[String] = sc.textFile("Spark-core/datas/user_visit_action.txt")

    val actionDataRDD: RDD[UserVisitAction] = actionRDD.map {
      action => {
        val data: Array[String] = action.split("_")
        UserVisitAction(
          data(0),
          data(1).toLong,
          data(2),
          data(3).toLong,
          data(4),
          data(5),
          data(6).toLong,
          data(7).toLong,
          data(8),
          data(9),
          data(10),
          data(11),
          data(12).toLong
        )
      }
    }
    actionDataRDD.cache()
//    TODO 计算分母: 首页点击数量
    val pageCount: Map[Long, Int] = actionDataRDD.map(
      action => {
        (action.page_id, 1)
      }
    ).reduceByKey(_ + _).collect().toMap

//    TODO 计算分子
//    1. 根据session id进行分组
    val sessionIDRDD: RDD[(String, Iterable[UserVisitAction])] = actionDataRDD.groupBy(_.session_id)

//    2. 分组后,根据访问时间进行排序
    val mvRDD: RDD[(String, List[((Long, Long), Int)])] = sessionIDRDD.mapValues(
      iter => {
        val sortList: List[UserVisitAction] = iter.toList.sortBy(_.action_time)
        val flowID: List[Long] = sortList.map(_.page_id)
        val pageFlowIds: List[(Long, Long)] = flowID.zip(flowID.tail)
        pageFlowIds.map(
          t => {
            (t, 1)
          }
        )
      }
    )

    val flatRDD: RDD[((Long, Long), Int)] = mvRDD.map(_._2).flatMap(list => list)
    val dataRDD: RDD[((Long, Long), Int)] = flatRDD.reduceByKey(_ + _)


//    TODO 计算单跳转换率, 分子/分母
    dataRDD.foreach{
      case ( (pageID1, pageID2), sum ) => {
        val page1Count: Int = pageCount.getOrElse(pageID1, 0)
        println(s"页面${pageID1}到${pageID2}单跳转换率为 : " + sum.toDouble/page1Count)
      }
    }

    sc.stop()
  }

  //用户访问动作表
  case class UserVisitAction(
    date: String,//用户点击行为的日期
    user_id: Long,//用户的 ID
    session_id: String,//Session 的 ID
    page_id: Long,//某个页面的 ID
    action_time: String,//动作的时间点
    search_keyword: String,//用户搜索的关键词
    click_category_id: Long,//某一个商品品类的 ID
    click_product_id: Long,//某一个商品的 ID
    order_category_ids: String,//一次订单中所有品类的 ID 集合
    order_product_ids: String,//一次订单中所有商品的 ID 集合
    pay_category_ids: String,//一次支付中所有品类的 ID 集合
    pay_product_ids: String,//一次支付中所有商品的 ID 集合
    city_id: Long//城市 id
  )
}
