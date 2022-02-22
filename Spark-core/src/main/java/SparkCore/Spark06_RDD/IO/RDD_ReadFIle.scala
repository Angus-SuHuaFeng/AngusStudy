package SparkCore.Spark06_RDD.IO

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object RDD_ReadFIle {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_Persist"))

    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2)))
//    Spark可以读取多种数据源的数据
//    sc.textFile()
//    sc.sequenceFile()
//    sc.objectFile()
//    sc.wholeTextFiles()
//    sc.hadoopFile()
  }
}
