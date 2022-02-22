package SparkCore.Spark03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 */
object RDD_Memory {
  def main(args: Array[String]): Unit = {
//    TODO 准备环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_Memory")
    val sc = new SparkContext(sparkConf)

//    TODO 从内存中创建RDD, 将内存中集合的数据作为处理的数据源
    val seq = Seq[Int](1,2,3,4)
//    俩个方法创建RDD
    /**
     * makeRDD在底层实现时调用了RDD对象的parallelize方法, 为了使方法好记,开发者特意创建了makeRDD这个方法,本质上一样,只是名称不同
     * sc.parallelize(seq)   : parallelize： 并行
     * sc.makeRDD(seq)
     */
    val rdd1 : RDD[Int] = sc.parallelize(seq)
    val rdd2 = sc.makeRDD(seq)
    rdd1.collect().foreach(println)
//    TODO 关闭环境
    sc.stop()
  }
}
