package SparkCore.Spark04

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_File_Par2 {
  def main(args: Array[String]): Unit = {
//    TODO 准备环境
    val rDD_Memory_ParConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_Memory_Par")
    val sc = new SparkContext(rDD_Memory_ParConf)
//    TODO 创建RDD
//    RDD的并行度 & 分区
    /**TODO 数据分区的分配
     * 分区存储问题(分区后怎么存储? 确定了数据和分区数后怎么将数据分配到每个分区呢?)
     *  1. 数据以行为单位进行读取
     *    Spark读取文件采用的是和Hadoop相同的方式读取, 所以是一行一行地读取, 和字节没有关系
     *  2. 数据读取时以偏移量为单位
     *            偏移量
     *    1@@  =>  012
     *    2@@  =>  345
     *    3    =>  6
     *
     *  3. 数据分区的偏移量范围的计算
     *  0 => [0,3] => 1
     *                2
     *  1 => [3.4] => 3
     *  2 => [4,5] =>
     *
     *  最终结果:
     *  第一个   第二个    第三个
     *    1       3
     *    2
     */

    val fileRDD: RDD[String] = sc.textFile("Spark-core/datas/1.txt")
    fileRDD.saveAsTextFile("output")
    //  TODO 关闭环境
    sc.stop()

  }
}
