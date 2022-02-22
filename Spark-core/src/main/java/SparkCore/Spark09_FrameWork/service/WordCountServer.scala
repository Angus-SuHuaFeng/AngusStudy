package SparkCore.Spark09_FrameWork.service

import SparkCore.Spark09_FrameWork.common.SparkService
import SparkCore.Spark09_FrameWork.dao.WordCountDao
import org.apache.spark.rdd.RDD

// 服务层
class WordCountServer extends SparkService{

  private val wordCountDao = new WordCountDao()

//  数据分析
   override def dataAnalysis()={
    val fileRDD: RDD[String] = wordCountDao.readFile("Spark/Spark-core/datas/word.txt")
    val words: RDD[String] = fileRDD.flatMap(_.split(" "))
    val mapRDD: RDD[(String, Int)] = words.map((_, 1))
    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect()
  }

}
