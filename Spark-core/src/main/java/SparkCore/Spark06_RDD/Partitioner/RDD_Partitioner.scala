package SparkCore.Spark06_RDD.Partitioner

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object RDD_Partitioner {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_Partitioner"))

    val rdd: RDD[(String, String)] = sc.makeRDD(List(
      ("nba", "xxxxxxx"),
      ("cba", "xxxxxxx"),
      ("nba", "xxxxxxx"),
      ("wnba", "xxxxxxx")
    ), 3)
    val newRDD: RDD[(String, String)] = rdd.partitionBy(MyPartitioner(3))

    newRDD.saveAsTextFile("out")

    sc.stop()
  }
//  自定义分区，继承Partitioner接口
  case  class MyPartitioner (partition : Int) extends Partitioner {
//    分区数
    override def numPartitions: Int = partition
//    根据数据的Key值返回分区所在的分区索引(从0开始)
    override def getPartition(key: Any): Int = {
      key match {
        case "nba" => 0
        case "wnba" => 1
        case _ => 2
      }
    }
  }
}
