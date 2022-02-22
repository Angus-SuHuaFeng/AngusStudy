package SparkStreaming.day01

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
 *  优雅地关闭: 流式任务需要 7*24 小时执行，但是有时涉及到升级代码需要主动停止程序，但是分
 * 布式程序，没办法做到一个个进程去杀死，所有配置优雅的关闭就显得至关重要了。
 * 使用外部文件系统来控制内部程序关闭。
 */
object SparkStreaming_kafka {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming_Window")
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    ssc.checkpoint("Spark-core/cp")
    val kafkaPara: Map[String, Object] = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG ->
        "BigData1:9092,BigData2:9092,BigData3:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "Angus",
      "key.deserializer" ->
        "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" ->
        "org.apache.kafka.common.serialization.StringDeserializer"
    )
    val kafkaDataDS: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Set("first"), kafkaPara)
    )
    val valDStream: DStream[String] = kafkaDataDS.map((record: ConsumerRecord[String, String]) => record.value())
    // 定义更新状态方法，参数 values 为当前批次单词频数，state 为以往批次单词频数
    val updateFunc = (values : Seq[Int], state: Option[Int]) => {
      val currentCount: Int = values.foldLeft(0)(_ + _)
      val previousCount: Int = state.getOrElse(0)
      Some(currentCount + previousCount)
    }
    val valueDS: DStream[(String, Int)] = valDStream.flatMap(_.split(" ")).map((_, 1))
    val stateDs: DStream[(String, Int)] = valueDS.updateStateByKey[Int](updateFunc)
    stateDs.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
