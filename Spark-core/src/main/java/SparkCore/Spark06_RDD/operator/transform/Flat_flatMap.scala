package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * flatMap: 将处理的数据进行扁平化后再进行映射处理，所以算子也称之为扁平映射
 * flatMap应用案例:
 *  将 List(List(1,2),3,List(4,5))进行扁平化操作
 */

object Flat_flatMap {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Flat_flatMap")
    val sc = new SparkContext(sparkConf)
//    TODO 将 List(List(1,2),3,List(4,5))进行扁平化操作
    val rdd: RDD[Any] = sc.makeRDD(List(List(1, 2), 3, List(4, 5)))
    val flatRDD: RDD[Any] = rdd.flatMap {
      case list: List[_] => list
      case data: Int => List(data)
    }
    flatRDD.collect().foreach(println)
    sc.stop()
  }
}
