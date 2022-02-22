package SparkStreaming.day01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
 * 无状态数据操作
 */
object SparkStreaming_join {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming_Transform")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val data1: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)
    val data2: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 8888)

    val map1: DStream[(String, Int)] = data1.map((_, 1))
    val map2: DStream[(String, Int)] = data2.map((_, 1))

//    所谓的DStream的Join操作，其实就是俩个RDD的Join
    val joinDS: DStream[(String, (Int, Int))] = map1.join(map2)

    joinDS.print()

    ssc.start()

    ssc.awaitTermination()

  }
}
