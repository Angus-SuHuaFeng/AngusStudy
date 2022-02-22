package SparkCore.SparkCore_Req

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object HotCategoryTop10_Optimize {
  def main(args: Array[String]): Unit = {
    /**
     * TODO 优化
     *  1. rdd重复使用   =>  使用缓存
     *  2. 存在大量的Shuffle操作(reduceByKey),影响性能  => 转换数据的结构,减少reduceByKey操作
     *
     */
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10_Optimize"))

    //    1.读取日志数据
    val actionRDD: RDD[String] = sc.textFile("Spark-core/datas/user_visit_action.txt")
//    TODO 优化1
    actionRDD.cache()

    /**
     * 2.将数据转换结构
     *  点击的场合: (品类ID, 点击量, 0, 0)
     *  下单的场合: (品类ID, 0, 下单量, 0)
     *  支付的场合: (品类ID, 0, 0, 支付数)
     */
//    TODO 优化2
    val flatmapRDD: RDD[(String, (Int, Int, Int))] = actionRDD.flatMap {
      action => {
        val datas: Array[String] = action.split("_")
        if (datas(6) != "-1") {
          //          点击场合
          List((datas(6), (1, 0, 0)))
        } else if (datas(8) != "null") {
          //          下单场合
          val id: Array[String] = datas(8).split(",")
          id.map(id => (id, (0, 1, 0)))
        } else if (datas(10) != "null") {
          //          支付场合
          val id: Array[String] = datas(10).split(",")
          id.map(id => (id, (0, 0, 1)))
        } else {
          Nil
        }
      }
    }
//    将RDD进行聚合操作  (品类ID, (点击数量,下单数量,支付数量)), 只有一次Shuffle
    val analysisRDD: RDD[(String, (Int, Int, Int))] = flatmapRDD.reduceByKey(
      (t1, t2) => {
        (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3)
      }
    )

    //    5.将品类进行排序，并取前10（点击数量 > 下单数量 > 支付数量）
    val result: Array[(String, (Int, Int, Int))] = analysisRDD.sortBy(_._2, false).take(10)

    //    6.将结果打印出来
    result.foreach(println)

    sc.stop()

  }


}
