package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD


/**
 * mapPartitionsWithIndex:
 *    可以让我们知道数据是来源于哪个分区
 *    例如：
 *    (0,1)     分区信息清晰明了
      (0,2)
      (1,3)
      (1,4)
    功能实现: 获取第二个数据分区的数据
 */
object Index_mapPartitionsWithIndex{
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Max_mapPartitions")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val rdd1: RDD[(Int, Int)] = rdd.mapPartitionsWithIndex(
      (index, data) => {
        data.map(
          i => (index, i)
        )
      }
    )
    rdd1.collect().foreach(println)

//    功能实现: 获取第二个数据分区的数据
    val rdd2: RDD[Int] = rdd.mapPartitionsWithIndex(
      (index, data) => {
        if (index == 1) {
          data
        } else {
          Nil.iterator
        }
      }
    )
    rdd2.collect().foreach(println)
    sc.stop()
  }
}
