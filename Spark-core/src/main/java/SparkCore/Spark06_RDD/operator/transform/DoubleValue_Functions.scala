package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object DoubleValue_Functions {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DoubleValue_Functions")
    val sc = new SparkContext(sparkConf)
//    TODO 双value类型
    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val rdd2: RDD[Int] = sc.makeRDD(List(3, 4, 5, 6), 2)

//    1.交集  [3,4]
    val intersectionRDD: RDD[Int] = rdd1.intersection(rdd2)
    println(intersectionRDD.collect().mkString(","))

//    2.并集  [1,2,3,4,3,4,5,6]
    val unionRDD: RDD[Int] = rdd1.union(rdd2)
    println(unionRDD.collect().mkString(","))

//    3.差集  [1,2]
    val subtractRDD: RDD[Int] = rdd1.subtract(rdd2)
    println(subtractRDD.collect().mkString(","))

//    4.拉链  ((1,3),(2,4),(3,5),(4,6))  将相同位置的元素放到一个元组中
//    拉链操作的俩个数据源分区数量必须保持一致
    val zipRDD: RDD[(Int, Int)] = rdd1.zip(rdd2)
    println(zipRDD.collect().mkString(","))
  }
}
