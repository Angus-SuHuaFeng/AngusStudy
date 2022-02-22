package SparkCore.Spark05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Operator_Transform_Par {
  def main(args: Array[String]): Unit = {
//    TODO 创建Spark环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_Operator_Transform")
    val sc = new SparkContext(sparkConf)
//    TODO 业务代码  算子 - map

    /**
     * 1. 当RDD计算只有一个分区的数据时，会按照按顺序执行逻辑，顺序执行
     *    例如下方，当我们把分区设为1后，结果为:
     *    -------------->1
          ************** 1
          -------------->2
          ************** 2
          -------------->3
          ************** 3
          -------------->4
          ************** 4
     *    发现是先执行了mapRDD中的逻辑代码，然后执行了mapRDD1中的逻辑代码，分区内是串行执行的
     *
     *    1，2，3，4
     *    由前面的讲到的从集合中创建RDD的分区规则我们知道1，2，3，4的分区如下：
     *    1，2
     *    3，4
     *    在1分区中，1必然先于2执行，在2分区中，3必然先于4执行
     *    而不同分区的元素是可以并发执行的，也就是说，1，3可以同时执行，或者1先3后，或3先1后
     *    从结果来看，完全符合上方的规则
     *    -------------->3
          -------------->1
          ************* 3
          ************* 1
          -------------->4
          -------------->2
          ************* 4
          ************* 2
     */
    val numRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)

    val mapRDD: RDD[Int] = numRDD.map(
      num => {
        println("-------------->" + num)
        num
      }
    )

    val mapRDD1: RDD[Int] = mapRDD.map(
      num => {
        println("************* " + num)
        num
      }
    )
    mapRDD1.collect()
//    TODO 关闭资源
    sc.stop()

  }
}
