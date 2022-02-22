package SparkCore.Spark06_RDD.Dependence

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object RDD_toDebugString {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.textFile("Spark-core/datas/word.txt")
    println(rdd.toDebugString)
    println("--------------------------------------")
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    println(words.toDebugString)
    println("--------------------------------------")
    val mapRDD: RDD[(String, Int)] = words.map((_, 1))
    println(mapRDD.toDebugString)
    println("--------------------------------------")
    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    println(reduceRDD.toDebugString)
    println("--------------------------------------")
    reduceRDD.collect().foreach(println)
  }
}
