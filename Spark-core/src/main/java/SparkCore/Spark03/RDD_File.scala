package SparkCore.Spark03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 */
object RDD_File {
  def main(args: Array[String]): Unit = {
//    TODO 准备环境 sc
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_File")
    val sc = new SparkContext(sparkConf)

//    TODO 从文件中创建RDD, 将文件中的数据作为处理的数据源
    /**
     * 1. path路径可以是单个文件的具体路径，也可以是目录名称，从整个目录中所以文件导入数据
     *    例如:
     *    1. 具体路径
     *    val rdd1: RDD[String] = sc.textFile("Spark-core/datas/1.txt")
     *    2. 目录
     *    val rdd2: RDD[String] = sc.textFile("Spark-core/datas/")
     *    3. 还可以是分布式文件存储系统路径： HDFS
     *    val rdd3: RDD[String] = sc.textFile("hdfs://BigData1:8020/test.txt")
     *    4. path路径还可以使用通配符 *  下方匹配1开头的文件，即匹配1.txt和11.txt
     *    val rdd4: RDD[String] = sc.textFile("datas/1*.txt")
     *
     */
    val rdd: RDD[String] = sc.textFile("Spark-core/datas/")
    rdd.collect().foreach(println)
    println("--------------------")

    /**
     * 2. 按文件读取，可以将数据的来源信息包含进去 ； sc.wholeTextFiles()
     * (file:/E:/IDEADemo/AngusStudy/Spark-core/datas/1.txt,Hello Scala World
        Hello Hadoop
        Spark Hadoop Spark Hello
        World
        )
     */
    val rdd1: RDD[(String, String)] = sc.wholeTextFiles("Spark-core/datas/")
    rdd1.collect().foreach(println)
//    TODO 关闭环境
    sc.stop()
  }
}
