package SparkStreaming.day01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
 * 有状态数据操作， 需要设置检查点
 */
object SparkStreaming_Window2 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming_Window")

    val ssc = new StreamingContext(sparkConf, Seconds(3))

    ssc.checkpoint("Spark-core/checkpoint")
    val line: ReceiverInputDStream[String] = ssc.socketTextStream("BigData1", 9991)

    val wordToOne: DStream[(String, Int)] = line.map((_, 1))

//    reduceByKeyAndWindow: 当窗口的范围比较大，但是滑动的步长比较小，那么可以采用增加数据和删除数据的方式
//    无需重复计算，提升性能
    val wordToCountDS: DStream[(String, Int)] = wordToOne.reduceByKeyAndWindow(
      (x: Int, y: Int) => {
        x + y
      },    // 加上新进入窗口的批次中的元素
      (x: Int, y: Int) => {
        x - y
      },    // 移除离开窗口的老批次元素
      Seconds(9), // 窗口时长
      Seconds(3)  // 窗口步长
    )
    wordToCountDS.print()

    ssc.start()

    ssc.awaitTermination()

    ssc.stop()
  }
}
