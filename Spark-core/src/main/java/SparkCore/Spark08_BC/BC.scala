package SparkCore.Spark08_BC

import org.apache.spark.{SparkConf, SparkContext}

object BC {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName(""))

    sc



  }
}
