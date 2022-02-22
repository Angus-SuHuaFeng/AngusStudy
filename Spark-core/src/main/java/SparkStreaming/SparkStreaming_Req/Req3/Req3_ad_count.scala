package SparkStreaming.SparkStreaming_Req.Req3

import SparkStreaming.SparkStreaming_Req.Req1.Req1_BlackList
import SparkStreaming.SparkStreaming_Req.Req1.Req1_BlackList.AdClickData
import SparkStreaming.SparkStreaming_Req.Util.kafkaUtil
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

import java.text.SimpleDateFormat
import java.util.Date
import scala.collection.mutable.ListBuffer

object Req3_ad_count {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Req2_area_city_ad_count")
    val ssc = new StreamingContext(sparkConf, Seconds(6))
    //    获取Kafka消费流
    val kafkaDataDS: InputDStream[ConsumerRecord[String, String]] = kafkaUtil.getKafkaStream(ssc, "first")

    //    将数据转换成样例类
    val adClickData: DStream[AdClickData] = kafkaDataDS.map(
      (kafkaData: ConsumerRecord[String, String]) => {
        val data: String = kafkaData.value()
        println(data)
        val datas: Array[String] = data.split(" ")
        AdClickData(datas(0), datas(1), datas(2), datas(3), datas(4))
      }
    )
    //    过滤掉黑名单数据
    val filterDS: DStream[AdClickData] = Req1_BlackList.filterByBlackList(adClickData)

//    开窗，10分钟统计一次
    val windowDStream: DStream[AdClickData] = filterDS.window(Minutes(10))
//    转换数据结构
    val sdf = new SimpleDateFormat("HH:mm")
    val adHmToCount: DStream[((String, String), Int)] = windowDStream.map(
      (data: AdClickData) => {
        val ts = sdf.format(new Date(data.ts.toLong))
        ((data.ad, ts), 1)
      }
    ).reduceByKey((_: Int) + (_: Int))

    val adDS: DStream[(String, List[(String, Int)])] = adHmToCount.map {
      case ((ad, ts), count) => {
        (ad, (ts, count))
      }
    }.groupByKey().mapValues(
      (iter: Iterable[(String, Int)]) => {
        iter.toList.sortBy((_: (String, Int))._2)
      }
    )
    adDS.foreachRDD(
      (rdd: RDD[(String, List[(String, Int)])]) => {
        rdd.foreach{
          case (ad, tsList) => {
            val list = ListBuffer[String]()
            tsList.foreach{
              case (ts, count) => {
                list.append(s""""xtime":"${ts}", "yval":"${count}"}""")
              }
            }
            println(list)
          }
        }
      }
    )


    ssc.start()

    ssc.awaitTermination()
  }

}



