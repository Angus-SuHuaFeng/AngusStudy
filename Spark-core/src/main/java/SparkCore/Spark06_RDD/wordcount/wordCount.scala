package SparkCore.Spark06_RDD.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object wordCount {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.makeRDD(List("Hello World ", "Hello Spark"))

//    groupBy
    def wc1(rdd: RDD[String]) ={
      val words : RDD[String] = rdd.flatMap(_.split(" "))
      val group: RDD[(String, Iterable[String])] = words.groupBy(words => words)
      val wordCount: RDD[(String, Int)] = group.mapValues(iter => iter.size)
      wordCount.collect().foreach(println)
    }
//  reduceByKey
    def wc2(rdd : RDD[String])={
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val mapRDD: RDD[(String, Int)] = words.map((_, 1))
      val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
      reduceRDD.collect().foreach(println)
    }
//    groupByKey
    def wc3(rdd : RDD[String])={
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val mapRDD: RDD[(String, Int)] = words.map((_, 1))
      val groupRDD: RDD[(String, Iterable[Int])] = mapRDD.groupByKey()
      val wordCount: RDD[(String, Int)] = groupRDD.mapValues(iter => iter.size)
      wordCount.collect().foreach(println)
    }
//    aggregateByKey
    def wc4(rdd : RDD[String])={
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val mapRDD: RDD[(String, Int)] = words.map((_, 1))
      val aggregateRDD: RDD[(String, Int)] = mapRDD.aggregateByKey(0)(_+_,_+_)
      aggregateRDD.collect().foreach(println)
    }
//    foldByKey
    def wc5(rdd : RDD[String])={
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val mapRDD: RDD[(String, Int)] = words.map((_, 1))
      val foldRDD: RDD[(String, Int)] = mapRDD.foldByKey(0)(_ + _)
      foldRDD.collect().foreach(println)
    }
//    combineByKey
    def wc6(rdd : RDD[String])={
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val mapRDD: RDD[(String, Int)] = words.map((_, 1))
      val combineRDD: RDD[(String, Int)] = mapRDD.combineByKey(
        v => v,
        (x: Int, y) => x + y,
        (x: Int, y: Int) => x + y
      )
      combineRDD.collect().foreach(println)
    }
//    countByKey
    def wc7(rdd : RDD[String])={
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val mapRDD: RDD[(String, Int)] = words.map((_, 1))
      val stringToLong: collection.Map[String, Long] = mapRDD.countByKey()
      println(stringToLong.mkString(","))
    }
//    countByValue
    def wc8(rdd : RDD[String])={
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val tupleToLong: collection.Map[String, Long] = words.countByValue()
      println(tupleToLong.mkString(","))
    }

    def wc9(rdd : RDD[String])={
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val mapRDD1: RDD[mutable.Map[String, Long]] = words.map(
        words => {
          mutable.Map[String, Long]((words, 1))
        }
      )

      val reduce: mutable.Map[String, Long] = mapRDD1.reduce(
        (map1, map2) => {
          map2.foreach {
            case (word, count) => {
              val newCount: Long = map1.getOrElse(word, 0L) + count
              map1.update(word, newCount)
            }
          }
          map1
        }
      )
      println(reduce)
    }
//    aggregate
    def wc10(rdd : RDD[String])={
      val words: RDD[String] = rdd.flatMap(_.split(" "))
      val mapRDD: RDD[mutable.Map[String, Long]] = words.map(
        word => mutable.Map[String, Long]((word, 1))
      )

//      mapRDD.aggregate(0)(,)
    }
//    fold
    def wc11(rdd : RDD[String])={

    }
    sc.stop()
  }
}
