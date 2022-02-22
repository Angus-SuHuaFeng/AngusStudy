package SparkCore.SparkCore_Req

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object HotCategoryTop10_SessionAnalysis {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10_SessionAnalysis"))

    val actionRDD: RDD[String] = sc.textFile("Spark-core/datas/user_visit_action.txt")
    actionRDD.cache()
    val top10Id: Array[String] = top10Category(actionRDD)

//    1.过滤原始数据，保留点击和前10品类ID
    val filterActionRDD: RDD[String] = actionRDD.filter {
      action => {
        val datas = action.split("_")
        if (datas(6) != "-1") {
          top10Id.contains(datas(6))
        } else {
          false
        }
      }
    }
//    2.根据品类ID和session id进行点击量的统计
    val reduceRDD: RDD[((String, String), Int)] = filterActionRDD.map {
      action => {
        val datas: Array[String] = action.split("_")
        ((datas(6), datas(2)), 1)
      }
    }.reduceByKey(_ + _)

//    3.将统计的结果进行结构的转换
//    ((品类ID,sessionID),sum) => (品类ID, (sessionID, sum))
    val mapRDD: RDD[(String, (String, Int))] = reduceRDD.map {
      case ((cid, sessionID), sum) => {
        (cid, (sessionID, sum))
      }
    }

//    4.相同的品类进行分组
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = mapRDD.groupByKey()

//    5.将分组后的数据进行点击量排序,取前10
    val resultRDD: RDD[(String, List[(String, Int)])] = groupRDD.mapValues {
      iter => {
        iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(10)
      }
    }

    resultRDD.collect().foreach(println)

  }

  def top10Category(actionRDD:RDD[String])={
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
    val result: Array[String] = analysisRDD.sortBy(_._2, false).take(10).map(_._1)
    result
  }
}
