package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * glom: 将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变
 * (相当于flatMap的逆处理，flatMat是扁平化，数组展开，而glom是将元素转换成数组)
 */

object MaxSum_glom {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("MaxSum_glom"))
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
//    TODO 计算所有分区最大值求和（分区内取最大值，分区间最大值求和）
    val rdd1: RDD[Array[Int]] = rdd.glom()
//    打印每个分区的元素
    rdd1.collect()foreach(data => println(data.mkString(",")))

    val rdd2: RDD[Int] = rdd1.map(
      array => {
        array.max
      }
    )
    println(rdd2.collect().sum)
  }
}
