package SparkCore.Spark06_RDD.Dependence

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object RDD_dependencies {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.textFile("Spark-core/datas/word.txt")
    println(rdd.dependencies)
    println("--------------------------------------")
    val words: RDD[String] = rdd.flatMap(_.split(" "))
    println(words.dependencies)
    println("--------------------------------------")
    val mapRDD: RDD[(String, Int)] = words.map((_, 1))
    println(mapRDD.dependencies)
    println("--------------------------------------")
    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    println(reduceRDD.dependencies)
    println("--------------------------------------")
    reduceRDD.collect().foreach(println)


  }
}
