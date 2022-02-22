package SparkCore.Spark06_RDD.IO

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_SaveFile {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_Persist"))

    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2)))

//    保存为文件
    rdd.saveAsTextFile("out1")
    rdd.saveAsObjectFile("out2")
//    只有key-Value类型的RDD才可以使用saveAsSequenceFile方法
    rdd.saveAsSequenceFile("out3")
  }
}
