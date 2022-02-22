package SparkCore.Spark05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
//    TODO 创建Spark环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_Operator_Transform")
    val sc = new SparkContext(sparkConf)
//    TODO 业务代码
    val fileRDD: RDD[String] = sc.textFile("Spark-core/datas/apache.log")
    val mapRDD: RDD[String] = fileRDD.map(
//      line => {
//        line.split(" ")(6)
////        或者:
////        val strings: String = line.split(" ").takeRight(1).mkString
////        strings
//      }
//      最简化版本:
      _.split(" ")(6)
    )
    mapRDD.collect().foreach(println)

//    TODO 关闭资源
    sc.stop()
  }
}
