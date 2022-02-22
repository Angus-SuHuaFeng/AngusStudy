package SparkCore.Spark04

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_File_Par3 {
  def main(args: Array[String]): Unit = {
//    TODO 准备环境
    val rDD_Memory_ParConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_Memory_Par")
    val sc = new SparkContext(rDD_Memory_ParConf)
//    TODO 创建RDD
//    RDD的并行度 & 分区
    /**TODO 数据分区的分配
     * 1. 按行读
     * 2. 按偏移量读
     *
     * 14 byte / 2 = 7 byte
     * 14 / 7 = 2 (分区)
     *              偏移量
       1234567@@   0 1 2 3 4 5 6 7 8
       89@@        9 10 11 12
       0           13
     *           偏移量区间包含元素
     * [0,7]  => 1234567        因为每个分区7个字节,所以偏移量读到 7 但是是按行读,所以7后边的换行符也被读了
     * [7,14] => 890            因此,第二个分区偏移量从9开始读, 读到14
     * 所以最后的结果是第一个文件中1234567  , 第二个文件中890
     */

    val fileRDD: RDD[String] = sc.textFile("C:\\Users\\Angus\\Desktop\\word1.txt")
    fileRDD.saveAsTextFile("output")
//   TODO 关闭环境
    sc.stop()
  }
}

