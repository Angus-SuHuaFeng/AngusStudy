package SparkCore.Spark07_ACC

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable


/**
 * 累加器可以用于简单数据的累加，从而避免Shuffle
 */
object wordCount_ACC {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_Persist"))

    val rdd: RDD[String] = sc.makeRDD(List("Hello", "World", "Spark", "Hello"))

//    累加器WordCount
//    创建累加器对象
    val myAccumulator = new MyAccumulator()
//    向Spark注册
    sc.register(myAccumulator,"wordCountAcc")
//    累加
    rdd.foreach{
      word => {
        myAccumulator.add(word)
      }
    }

    println(myAccumulator)

    sc.stop()
  }
  /*
    自定义累加器
    1. 继承AccumulatorV2
      IN: 累加器输入的数据类型  String
      OUT:  累加器返回的数据类型  mutable.Map[String,Long]
   */

  class MyAccumulator extends AccumulatorV2[String, mutable.Map[String,Long]] {
    private var wcMap =  mutable.Map[String,Long]()
//    判断是否是初始状态
    override def isZero: Boolean = wcMap.isEmpty
//    复制一个新的累加器
    override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = {
        new MyAccumulator()
    }
//    重置累加器
    override def reset(): Unit = {
      wcMap.clear()
    }
//    获取累加器需要计算的值
    override def add(word: String): Unit = {
      val newCount: Long = wcMap.getOrElse(word, 0L) + 1
      wcMap.update(word,newCount)
    }
//    合并累加器
    override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
      val map1 = this.wcMap
      val map2 = other.value

      map2.foreach{
        case (word, count) => {
          val newCount: Long = map1.getOrElse(word, 0L) + count
          map1.update(word,newCount)
        }
      }
    }
//    累计器结果
    override def value: mutable.Map[String, Long] = wcMap
  }
}
