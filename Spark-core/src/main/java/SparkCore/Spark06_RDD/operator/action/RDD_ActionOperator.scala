package SparkCore.Spark06_RDD.operator.action

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object RDD_ActionOperator {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_ActionOperator"))

    val rdd: RDD[Int] = sc.makeRDD(List(5, 1, 2, 3, 4), 2)

//    TODO 行动算子

    /**
     *  所谓行动算子,其实就是触发作业(Job)执行的方法
     *  底层代码调用的是环境对象的runJob方法
     *  底层代码中会创建ActiveJob并提交执行
     *  返回值直接是结果,而不是RDD
     */

//    1. reduce : 聚集 RDD 中的所有元素，先聚合分区内数据，再聚合分区间数据
    val reduce: Int = rdd.reduce(_ + _)
    println(reduce)

//    2. collect : 在驱动程序中，以数组 Array 的形式返回数据集的所有元素
    val array: Array[Int] = rdd.collect()
    println(array.mkString("Array(", ", ", ")"))

//    3. 数据源中数据的个数
    val cnt: Long = rdd.count()
    println(cnt)

//    4. first : 返回 RDD 中的第一个元素
    val first: Int = rdd.first()
    println(first)

//    5. take : 返回一个由 RDD 的前 n 个元素组成的数组
    val takeArray: Array[Int] = rdd.take(3)
    println(takeArray.mkString("Array(", ", ", ")"))

//    6. takeOrdered 返回该 RDD 排序后的前 n 个元素组成的数组
    val takeOrdered: Array[Int] = rdd.takeOrdered(3)
    println(takeOrdered.mkString("Array(", ", ", ")"))

    /**
     * 7. aggregate 分区的数据通过初始值和分区内的数据进行聚合，然后再和初始值进行分区间的数据聚合
     *  aggregateByKey: 初始值只参与分区内计算 (转换算子)
     *  aggregate: 初始值会参与分区内和分区间的计算
     */
    val aggregate: Int = rdd.aggregate(0)(_ + _, _ + _)
    println(aggregate)

//    6. fold 当分区内和分区间计算方法相同时, 可以用fold简化
    val fold: Int = rdd.fold(0)(_ + _)
    println(fold)

//    7. countByKey : 统计每种 key 的个数, countByValue : 统计每种 value 的个数
    val kvRDD: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 1), ("c", 2), ("b", 3)
    ))

    val map: collection.Map[(String, Int), Long] = kvRDD.countByValue()
    println(map)

    val stringToLong: collection.Map[String, Long] = kvRDD.countByKey()
    println(stringToLong)


//    8. save算子
    kvRDD.saveAsTextFile("output")

    kvRDD.saveAsObjectFile("output1")
//    saveAsSequenceFile算子要求数据的格式必须为K-V类型
    kvRDD.saveAsSequenceFile("output2")

    sc.stop()
  }
}

