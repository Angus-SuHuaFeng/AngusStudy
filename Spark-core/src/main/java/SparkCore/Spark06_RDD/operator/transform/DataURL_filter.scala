package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * filter:
 *    将数据根据指定的规则进行筛选过滤，符合规则的数据保留，不符合规则的数据丢弃。
 *    过滤后，分区不变，但是分区内的数据可能不均衡，生产环境下，可能会出现数据倾斜。(例如，1分区的数据被过滤了90%，2分区的数据被过滤了10%，这样数据就发生了倾斜)
 *
 */
object DataURL_filter {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DataURL_filter")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.textFile("Spark-core/datas/apache.log")
//    TODO 从服务器日志数据 apache.log 中获取 2015 年 5 月 17 日的请求路径
    val rdd1: RDD[String] = rdd.filter(
      (line: String) => {
        val dates: String = line.split(" ")(3)
        dates.startsWith("17/05/2015")
      }
    )
    val rdd2: RDD[String] = rdd1.map(
      (_: String).split(" ")(6)
    )
    rdd2.collect().foreach(println)
  }
}
