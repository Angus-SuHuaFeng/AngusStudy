package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import java.text.SimpleDateFormat
import java.util.Date

/**
 * groupBy:
 *    将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样的操作称之为 shuffle。极限情况下，数据可能被分在同一个分组中
 * groupBy应用案例:
 *    从服务器日志数据 apache.log 中获取每个时间段访问量
 */

object View_groupBy {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("View_groupBy")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.textFile("Spark-core/datas/apache.log")

    val rdd1: RDD[(String, Iterable[(String, Int)])] = rdd.map(
      line => {
        val dates: String = line.split(" ")(3)
        val dateFormat = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val date: Date = dateFormat.parse(dates)
        val dateFormat1 = new SimpleDateFormat("HH")
        val hour: String = dateFormat1.format(date)
        (hour, 1)
      }
    ).groupBy((_: (String, Int))._1)

    rdd1.map {
      case (hour, iter) => {
        (hour, iter.size)
      }
    }.collect().foreach(println)
    sc.stop()
  }
}
