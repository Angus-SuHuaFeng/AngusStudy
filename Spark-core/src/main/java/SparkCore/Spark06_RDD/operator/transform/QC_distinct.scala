package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * distinct:
 *    将数据集中重复的数据去重
 *    源码中去重的实现:
 *    map(x => (x, null)).reduceByKey((x, _) => x, numPartitions).map(_._1)
 *    先将数据变为元组 (x, null) 再进行聚合，聚合后数据为没有重复元素的元组，最后再使用map取元组第一个值
 */
object QC_distinct {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("QC_distinct")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 5, 6, 6, 6, 3, 7))

    val rdd1: RDD[Int] = rdd.distinct()
    rdd1.collect().foreach(println)

  }
}
