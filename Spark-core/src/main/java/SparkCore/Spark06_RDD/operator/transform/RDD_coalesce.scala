package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * coalesce:
 *      根据数据量缩减分区，用于大数据集过滤后，提高小数据集的执行效率
 *      当 spark 程序中，存在过多的小任务的时候，可以通过 coalesce 方法，收缩合并分区，减少分区的个数，减小任务调度成本
 *
 *      coalesce方法默认不会将分区的数据打乱重组
 *      这种情况下缩减分区可能会导致数据不均衡，出现数据倾斜
 *      如果想要数据均衡，可以进行shuffle处理，将第二个参数设置为true（默认为false）
 *      但是数据没有顺序，shuffle就是没有顺序的操作
 *      coalesce还可以扩大分区，扩大分区意味着要打乱原分区的数据
 *      所以需要进行shuffle操作将数据打乱才能重组，如果不shuffle是没有意义的，不起作用
 */
object RDD_coalesce {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_coalesce")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1,2,3,4,5,6),3)
    rdd.saveAsTextFile("output1")
//    TODO 缩减分区
    val rdd1: RDD[Int] = rdd.coalesce(2,shuffle = true)

    rdd1.saveAsTextFile("output2")
//    TODO 扩大分区
    /**
     *  俩种方法：
     *    1. 使用coalesce方法，设置shuffle = true
     *    2. 使用repartition方法，其实repartition方法的底层就是调用的coalesce方法
     */
//    1
    val rdd2: RDD[Int] = rdd1.coalesce(3, shuffle = true)
//    2
    val rdd3: RDD[Int] = rdd1.repartition(3)
    rdd2.saveAsTextFile("output3")

    sc.stop()
  }
}
