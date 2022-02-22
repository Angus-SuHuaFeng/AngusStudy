package SparkStreaming.day01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
 * 无状态数据操作
 */
object SparkStreaming_Window1 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming_Window")

    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val line: ReceiverInputDStream[String] = ssc.socketTextStream("BigData1", 9991)

    val wordToOne: DStream[(String, Int)] = line.map((_, 1))
    //    窗口的范围应该是采集周期的整数倍
    //    窗口是可以滑动的，但是在默认情况下，按照一个周期进行滑动
    //    这样的话可能会出现重复数据的计算，为了避免这种情况，可以改变滑动的步长
    //    例如：这里是3秒一个周期，而窗口包含六秒，在不设置步长的情况下默认滑动一个周期，
    //    所以就会出现一个周期的重复数据，如果设置了步长为来个周期，这样就不会有重复数据了

    val windowDS: DStream[(String, Int)] = wordToOne.window(Seconds(6),Seconds(6))

    val wordToCount: DStream[(String, Int)] = windowDS.reduceByKey(_ + _)

    wordToCount.print()

    ssc.start()

    ssc.awaitTermination()

    ssc.stop()
  }
}
