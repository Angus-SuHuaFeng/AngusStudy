package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * sample: 根据指定的规则从数据集中抽取数据
 *
 *
 */
object CJ_sample {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("CJ_sample")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1,2,3,4,5,6,7,8,9,10))

    println(rdd.sample(
      false,    // True: 有放回抽取    False: 无放回抽取
      0.4,
      1
    ).collect().mkString(","))

    sc.stop()
  }
}
