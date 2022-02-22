package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * map应用案例：
 * 从服务器日志数据 apache.log 中获取用户请求 URL 资源路径
 *
 */
object URL_mapTest {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("URL_mapTest")
    val sc = new SparkContext(sparkConf)

    val fileRDD: RDD[String] = sc.textFile("Spark-core/datas/apache.log")

    val mapRDD: RDD[String] = fileRDD.map(
      (_: String).split(" ")(6)
    )
    mapRDD.collect().foreach(println)
    sc.stop()
  }
}
