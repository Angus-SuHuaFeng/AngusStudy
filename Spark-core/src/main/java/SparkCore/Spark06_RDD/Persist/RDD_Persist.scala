package SparkCore.Spark06_RDD.Persist

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 持久化
 */
object RDD_Persist {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_Persist"))

    val list = List("Hello Scala","Hello Spark")

    val rdd: RDD[String] = sc.makeRDD(list)

    val flatRDD: RDD[String] = rdd.flatMap(_.split(" "))

    val mapRDD: RDD[(String, Int)] = flatRDD.map((_,1))

//
    /**
     * 缓存，将数据保存在内存中或者文件中，是实现持久化
     * cache方法: 只能将数据保存在内存中
     * persist方法: 可以设置级别(StorageLevel)，不同的级别的缓存方式不同，
     *    可以在内存中和磁盘中，存在磁盘中是以临时文件的形式保存的，在任务执行完毕后会删除
     * 缓存(持久化操作)是在触发行动算子时才会执行
     * 作用: RDD的持久化操作不一定只是为了重用
     *      1. 方便重用，提高性能
     *      2. 在某些转化算子执行的过程中时间比较长，比较耗费资源，我们可以执行持久化操作，
     *         如果后边的执行出现了什么问题，可以直接使用缓存中的数据而不用重新从头执行，提高容错
     */
    mapRDD.cache()
    mapRDD.persist(StorageLevel.DISK_ONLY)
    mapRDD.checkpoint()
    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)

    reduceRDD.collect().foreach(println)
//    RDD不保存数据
//    如果一个RDD需要重复使用，那么需要从头再执行一次
//    所以如果想重复使用而又不想从头执行来节省性能，这就需要持久化了
    val groupRDD: RDD[(String, Iterable[Int])] = mapRDD.groupByKey()

    groupRDD.collect().foreach(println)

    sc.stop()
  }
}
