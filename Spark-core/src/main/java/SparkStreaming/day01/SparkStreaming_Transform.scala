package SparkStreaming.day01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 *  无状态数据操作
 */
object SparkStreaming_Transform {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming_Transform")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val lineDS: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)

//    transform方法可以将底层的rdd获取到,然后进行操作
//    1. DStream功能不完善
//    2. 需要代码周期性的执行
//    Code: Driver端
    lineDS.transform(
      rdd => {
//        Code: Driver端(周期性执行)
        rdd.map(
//          Code: Executor端
          str => str
        )
      }
    )
//    Code: Driver端
    lineDS.map(
//    Code: Executor端
      str => str
    )
  }

}
