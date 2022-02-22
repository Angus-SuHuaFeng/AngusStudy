package SparkCore.Spark01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount2 {
  def main(args: Array[String]): Unit = {
//    Application
//    Spark框架
//    TODO 建立和Spark框架的连接
    val sparkConf = new SparkConf().setMaster("BigData1").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

//    TODO 执行业务操作
//    1. 按行读取文件  "hello world"
    val linesRDD : RDD[String] = sc.textFile("datas")

//    2. 将每行数据进行拆分为一个一个的单词  => hello , world
    val wordsRDD = linesRDD.flatMap(_.split(" "))

//    优化： 将单词转化成元组 例如(Hello, 1)
    val wordToOneRDD = wordsRDD.map ((_, 1))
//    3. 将数据根据单词进行分组，便于统计 => (hello, hello), (world, world)
    val groups: RDD[(String, Iterable[(String, Int)])]= wordToOneRDD.groupBy{
      w => w._1
    }

//    4. 对分组后的数据进行聚合转换
    val wordToCount = groups.map {
      case (word, list) => {
        list.reduce(
          (t1,t2) => {
            (word, t1._2 + t2._2)
          }
        )
      }
    }

//    5. 将转换结果采集到控制台
    val array = wordToCount.collect()
    array.foreach(println)

//    TODO 关闭链接
    sc.stop()

  }
}
