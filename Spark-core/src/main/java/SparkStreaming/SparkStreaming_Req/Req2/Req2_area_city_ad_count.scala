package SparkStreaming.SparkStreaming_Req.Req2

import SparkStreaming.SparkStreaming_Req.Util.{JDBCUtil, kafkaUtil}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import java.sql.Connection
import java.text.SimpleDateFormat
import java.util.Date

object Req2_area_city_ad_count {
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

    val reduceDS: DStream[((String, String, String, String), Int)] = adClickData.map(
      (date: AdClickData) => {
        val sdf = new SimpleDateFormat("yyyy-MM-dd")
        val day: String = sdf.format(new Date(date.ts.toLong))
        val area: String = date.area
        val city: String = date.city
        val adid: String = date.ad
        ((day, area, city, adid), 1)
      }
    ).reduceByKey((_: Int) + (_: Int))

    reduceDS.foreachRDD(
      (rdd: RDD[((String, String, String, String), Int)]) => {
        rdd.foreachPartition(
          (iter: Iterator[((String, String, String, String), Int)]) => {
            val connect: Connection = JDBCUtil.getConnect
            iter.foreach{
              case ((day, area, city, adid), count) =>
                JDBCUtil.executeUpdate(
                  connect,
                  """
                    |insert into area_city_ad_count (dt, area, city, adid, count)
                    |values (?, ?, ?, ?, ?)
                    |on DUPLICATE KEY
                    |update count = count + ?
                    |""".stripMargin,
                  Array(day,area,city,adid,count,count)
                )
            }
            connect.close()
          }
        )
      }
    )

    ssc.start()

    ssc.awaitTermination()
  }
  case class AdClickData(ts:String,area:String,city:String,user:String,ad:String)
}



