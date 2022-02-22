package SparkCore.Spark04

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_File_Par1 {
  def main(args: Array[String]): Unit = {
//    TODO 准备环境
    val rDD_Memory_ParConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_Memory_Par")
    val sc = new SparkContext(rDD_Memory_ParConf)
//    TODO 创建RDD
//    RDD的并行度 & 分区
    /**
     * 1.分区数量问题
     *  创建RDD时我们发现,我们没有传递分区数,说明textFile也有默认的分区数,我们点进去看
     *  ctrl+左键 ->  发现:
     *    minPartitions : 最小分区数 , 它的值为defaultMinPartitions, 这里说的是 最小分区数,并不是确切的分区数
     *    def defaultMinPartitions: Int = math.min(defaultParallelism, 2)
     *    点进去为上方函数, 发现是计算defaultParallelism和2的最小值. defaultParallelism在之前提到过, 并且一步一步去找了实现源码
     *    override def defaultParallelism(): Int =
              scheduler.conf.getInt("spark.default.parallelism", totalCores)
     *    它就是spark.default.parallelism配置项, 如果没有配置则默认为当前环境的可使用核数
     *
     *  同makeRDD相同,也有第二个参数可以设置最小分区
     *  实际分区数量的计算方式同Hadoop的相同
     *    totalSize = 7   字节数
     *    goalSize = 7/2 = 3 每个分区字节数
     *    7 / 3 = 2...1  => 2+1=3个分区  1.1倍原则,如果超出的字节为每个分区的1.1倍以上,就新开一个分区
     */

    val fileRDD: RDD[String] = sc.textFile("Spark-core/datas/1.txt",2)
    fileRDD.saveAsTextFile("output")
    //  TODO 关闭环境
    sc.stop()

  }
}
