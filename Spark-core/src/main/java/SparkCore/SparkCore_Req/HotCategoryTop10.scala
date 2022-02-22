package SparkCore.SparkCore_Req

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object HotCategoryTop10 {
  def main(args: Array[String]): Unit = {
//    TODO Top10热门品类
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10"))

//    1.读取日志数据
    val rdd: RDD[String] = sc.textFile("Spark-core/datas/user_visit_action.txt")

//    2.统计品类的点击数量 (品类ID, 点击量)
    val clickActionRDD: RDD[String] = rdd.filter(
      action => {
        val datas = action.split("_")
        datas(6) != "-1"
      }
    )

    val clickCountRDD: RDD[(String, Int)] = clickActionRDD.map(
      action => {
        val datas: Array[String] = action.split("_")
        (datas(6), 1)
      }
    ).reduceByKey(_ + _)


//    3.统计品类的下单数量 (品类ID, 下单量)
    val orderActionRDD: RDD[String] = rdd.filter(
      action => {
        val datas = action.split("_")
        datas(8) != "null"
      }
    )

    val orderCountRDD: RDD[(String, Int)] = orderActionRDD.flatMap(
      action => {
        val datas = action.split("_")
        val cid = datas(8)
        val cids: Array[String] = cid.split(",")
        cids.map(id => (id, 1))
      }
    ).reduceByKey(_ + _)

//    4.统计品类的支付数量 (品类ID, 支付量)
    val payActionRDD: RDD[String] = rdd.filter(
      action => {
        val datas = action.split("_")
        datas(10) != "null"
      }
    )

    val payCountRDD: RDD[(String, Int)] = payActionRDD.flatMap(
      action => {
        val datas: Array[String] = action.split("_")
        val cid: String = datas(10)
        val cids: Array[String] = cid.split(",")
        cids.map(id => (id, 1))
      }
    ).reduceByKey(_ + _)

//    5.将品类进行排序，并取前10（点击数量 > 下单数量 > 支付数量）
//      元组排序：先比较第一个，再比较第二个，再比较第三个，以此类推
//      (品类ID, (点击数量,下单数量,支付数量))

//    将三个RDD组合成一个RDD，使用cogroup组合起来, connect + group
    val cogroupRDD: RDD[(String, (Iterable[Int], Iterable[Int], Iterable[Int]))] =
                      clickCountRDD.cogroup(orderCountRDD, payCountRDD)
    val analysisRDD: RDD[(String, (Int, Int, Int))] = cogroupRDD.mapValues {
      case (clickIter, orderIter, payIter) => {
        (clickIter.head, orderIter.head, payIter.head)
      }
    }
//    TODO cogroup可能会存在shuffle,影响性能, 使用其他方法将三个RDD数据合并  => 使用union

    val result: Array[(String, (Int, Int, Int))] = analysisRDD.sortBy(_._2, false).take(10)

//    6.将结果打印出来
    result.foreach(println)

    sc.stop()

  }
}
