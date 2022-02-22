package SparkStreaming.SparkStreaming_Req.Req1

import SparkStreaming.SparkStreaming_Req.Util.{JDBCUtil, kafkaUtil}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import java.sql.{Connection, PreparedStatement, ResultSet}
import java.text.SimpleDateFormat
import java.util.Date
import scala.collection.mutable.ListBuffer

object Req1_BlackList {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Req_BlackList")
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

    val filterDS: DStream[AdClickData] = filterByBlackList(adClickData)
    val filterRDDToCount: DStream[((String, String, String), Int)] = filterDS.map(
      data => {
        val sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm")
        val date = sdf.format(new Date(data.ts.toLong))
        val user = data.user
        val ad = data.ad
        ((date, user, ad), 1)
      }
    ).reduceByKey(_ + _)

    addBlackList(filterRDDToCount)
    filterDS.cache()
    filterDS.count().print()

    ssc.start()

    ssc.awaitTermination()
  }
  case class AdClickData(ts:String,area:String,city:String,user:String,ad:String)
  def getBlack_List(connect: Connection)={
    val blackList = ListBuffer[String]()
    val preparedStatement: PreparedStatement = connect.prepareStatement("select userid from black_list")
    val rs: ResultSet = preparedStatement.executeQuery()
    while (rs.next()) {
      blackList.append(rs.getString(1))
    }
    rs.close()
    preparedStatement.close()
    blackList
  }

  def  filterByBlackList(adClickData : DStream[AdClickData]) ={
    val filterRDDToCountDS: DStream[AdClickData] = adClickData.transform(
      (rdd: RDD[AdClickData]) => {
        //    TODO 周期性获取黑名单数据
        val connect: Connection = JDBCUtil.getConnect
        val blackList: ListBuffer[String] = getBlack_List(connect)
        //          过滤黑名单中的数据
        val filterRDD: RDD[AdClickData] = rdd.filter(
          data => {
            !blackList.contains(data.user)
          }
        )
        connect.close()
        filterRDD
      }
    )
    filterRDDToCountDS
  }

  def addBlackList (filterRDDToCountDS: DStream[((String, String, String), Int)]): Unit = {
    filterRDDToCountDS.foreachRDD(
      rdd => {
        rdd.foreachPartition(
          iter => {
            val connection: Connection = JDBCUtil.getConnect
            iter.foreach{
              case ((dt, user, ad), count) =>
                //            数据更新到统计表
                JDBCUtil.executeUpdate(connection,
                  """
                    |INSERT INTO user_ad_count (dt,userid,adid,count)
                    |VALUES (?,?,?,?)
                    |ON DUPLICATE KEY
                    |UPDATE count=count+?
            """.stripMargin, Array(dt, user, ad, count, count))
                val ct: Long = JDBCUtil.getDataFromMysql(connection, "select count from user_ad_count where dt = ? and  userid=? and adid =?", Array(dt, user, ad))
                if (ct >= 30) {
                  JDBCUtil.executeUpdate(connection, "INSERT INTO black_list (userid) VALUES (?) ON DUPLICATE KEY update userid=?", Array(user, user))
                }
            }
            connection.close()
          }
        )
      }
    )
  }
}