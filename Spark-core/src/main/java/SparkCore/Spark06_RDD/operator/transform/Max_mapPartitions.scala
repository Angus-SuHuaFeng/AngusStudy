package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * mapPartitions应用案例：
 * 获取每个数据分区的最大值,mapPartitions详细介绍见： Spark05/RDD_Operator_Transform_mapPar.scala
 */
object Max_mapPartitions {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Max_mapPartitions")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val mapParRDD: RDD[Int] = rdd.mapPartitions(
      iter => {
        List(iter.max).iterator
      }
    )
    mapParRDD.collect().foreach(println)
    sc.stop()
  }
}
