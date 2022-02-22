package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_sortBy {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_sortBy")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a",1),("b",2),("c",3)),2)

    /**
     * 根据指定的规则对数据进行排序，默认为升序，第二个参数设置为false则为降序
     * 很显然排序会将每个分区的数据打乱，所以一定有shuffle操作，但是默认不改变分区
     */
    val rdd1: RDD[(String, Int)] = rdd.sortBy(t => t._2)

    rdd1.collect().foreach(println)

    sc.stop()
  }
}
