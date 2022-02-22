package SparkStreaming.SparkStreaming_Req.Req1

import SparkStreaming.SparkStreaming_Req.Util.PropertiesUtil
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import java.util.Properties
import scala.collection.mutable.ListBuffer
import scala.util.Random

object Req1_MockData {
  def main(args: Array[String]): Unit = {
//    生成模拟数据
//    时间戳 省份 城市 用户 广告
//    Application => Kafka => SparkStreaming => Analysis
//    获取配置文件 config.properties 中的 Kafka 配置参数
    val config: Properties = PropertiesUtil.load("Spark-core/src/main/resources/config.properties")
    val broker: String = config.getProperty("kafka.broker.list")
    val topic = "first"
    val producer: KafkaProducer[String, String] = createKafkaProducer(broker)
    while (true) {
      mockData().foreach(
        (data: String) => {
          println(data)
          val record = new ProducerRecord[String, String](topic, data)
          producer.send(record)
        }
      )
      Thread.sleep(2000)
    }
  }

  def mockData(): ListBuffer[String] ={
    val list: ListBuffer[String] = ListBuffer[String]()
    val areaList: ListBuffer[String] = ListBuffer[String]("华北","华东","华南")
    val cityList: ListBuffer[String] = ListBuffer[String]("北京","上海","深圳")
    for (i <- 1 to 30){
      val area: String = areaList(new Random().nextInt(3))
      val city: String = cityList(new Random().nextInt(3))
      val userid: Int = new Random().nextInt(100)
      val adID: Int = new Random().nextInt(10)
      list.append(s"${System.currentTimeMillis()} ${area} ${city} ${userid} ${adID}")
    }
    list
  }
  def createKafkaProducer(broker: String): KafkaProducer[String, String] = {
    // 创建配置对象
    val prop = new Properties()
    // 添加配置
    prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker)
    prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    // 根据配置创建 Kafka 生产者
    val producer = new KafkaProducer[String, String](prop)
    producer
  }
}



