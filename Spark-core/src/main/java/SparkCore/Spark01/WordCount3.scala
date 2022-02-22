package SparkCore.Spark01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount3 {
  def main(args: Array[String]): Unit = {
//    Application
//    Spark框架
//    TODO 建立和Spark框架的连接
    val sparkConf = new SparkConf().setMaster("BigData1").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

//    TODO 执行业务操作
//    1. 按行读取文件  "hello world"
    val lines : RDD[String] = sc.textFile("datas")

//    2. 将每行数据进行拆分为一个一个的单词  => hello , world
    val words = lines.flatMap(_.split(" "))

//    优化： 将单词转化成元组 例如(Hello, 1)
    val wordToOne = words.map {
      word => (word, 1)
    }

//    Spark框架提供了更多的功能, 可以将分组和聚合使用一个方法实现
//    reduceByKey() : 可以对相同的key的value值进行reduce聚合
    val wordToCount = wordToOne.reduceByKey(_ + _)


//    5. 将转换结果采集到控制台
    val array = wordToCount.collect()
    array.foreach(println)

//    TODO 关闭链接
    sc.stop()

  }
}
