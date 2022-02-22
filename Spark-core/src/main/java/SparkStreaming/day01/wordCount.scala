package SparkStreaming.day01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object wordCount {
  def main(args: Array[String]): Unit = {

//   TODO 创建环境对象
    /*
        StreamingContext创建时，需要俩个参数:
          1. 表示环境配置
          2. 表示批量处理的周期(采集周期)
     */
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

//   TODO 逻辑处理
//    获取端口数据
    val linesStream: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)

    val words: DStream[String] = linesStream.flatMap(_.split(" "))

    val wordToOne: DStream[(String, Int)] = words.map((_, 1))

    val wordToCount: DStream[(String, Int)] = wordToOne.reduceByKey(_ + _)

    wordToCount.print()

    ssc.start()

    ssc.awaitTermination()

//   TODO 关闭环境
    ssc.stop()
  }
}
