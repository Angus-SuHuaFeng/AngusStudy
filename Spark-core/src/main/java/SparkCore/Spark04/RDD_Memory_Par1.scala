package SparkCore.Spark04

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Memory_Par1 {
  def main(args: Array[String]): Unit = {
//    TODO 准备环境
    val rDD_Memory_ParConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_Memory_Par")
    val sc = new SparkContext(rDD_Memory_ParConf)
//    TODO 创建RDD
//    RDD的并行度 & 分区
    /**
     * 1.分区数量问题
     * makeRDD方法可以传递第二个参数, 这个参数表示分区的数量
     * 如果不传递第二个参数, makeRDD方法会使用默认值: defaultParallelism
     * ctrl+左键 makeRDD  ->  defaultParallelism  -> defaultParallelism  发现在一个特质中
     * ctrl+H 查看实现 ctrl+R 查找 defaultParallelism, -> ctrl+左键 defaultParallelism() 到SchedulerBackend, 发现也是一个特质
     * 继续ctrl+H 查看实现 , 这里选择Local , ctrl+R 查找 defaultParallelism 最终找到:
     *   override def defaultParallelism(): Int =
              scheduler.conf.getInt("spark.default.parallelism", totalCores)
     * 说明这个默认值是通过读取sparkConf中的spark.default.parallelism这个配置进行确定的,
     * 如果没有配置spark.default.parallelism, 就会默认按照totalCores(当前运行环境最大可用核数)进行配置
     */

    val rdd1: RDD[Int] = sc.makeRDD(
      List(1, 2, 3, 4),2
    )

    /**
     * 结果为俩个文件
     * part-00000
     * part-00001
     */
    rdd1.saveAsTextFile("output1")

//    不传递第二个参数则为当前环境的核数,结果发现有16个分区文件
    val rdd2: RDD[Int] = sc.makeRDD(
      List(1, 2, 3, 4)
    )
    rdd2.saveAsTextFile("output2")

    //  TODO 关闭环境
    sc.stop()

    /**
     *  也可以通过配置spark.default.parallelism设定分区数, 但是需要在创建环境前进行配置
     *
     *  再次运行发现:
     *   output1中有2个
     *   output2中有16个
     *   output3中有4个
     */
    rDD_Memory_ParConf.set("spark.default.parallelism", "4")
    val sparkContext = new SparkContext(rDD_Memory_ParConf)
    val rdd3: RDD[Int] = sparkContext.makeRDD(
      List(1, 2, 3, 4)
    )
    rdd3.saveAsTextFile("output3")
    sparkContext.stop()
  }
}
