 package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Key_Value_Functions {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Key_Value_Functions")
    val sc = new SparkContext(sparkConf)

//    TODO 算子 - （key-value类型）
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)

    val rdd1: RDD[(Int, Int)]= rdd.map((_, 1))

    /**
     * partitionBy根据指定的分区规则对数据进行重新分区
     */
//    rdd1.partitionBy(new HashPartitioner(3)).saveAsTextFile("output")


    /**
     * reduceByKey: 对相同的Key的Value进行聚合
     *
     * scala语言中一般的聚合操作都是俩俩操作，Spark是基于Scala开发的，所以它的聚合也是俩俩操作的
     *
     * reduceByKey中如果key的数据只有一个，是不会参与运算的，例如kvRDD中key为b，c的元组，并没有参与运算
     */
    val kvRDD: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 1), ("b", 1), ("c", 1)))
    val reduceRDD: RDD[(String, Int)] = kvRDD.reduceByKey((x: Int, y: Int) => x + y)
    reduceRDD.collect().foreach(println)

    /**
     * groupByKey: 将数据源中相同Key的数据分在一个组中，形成一个对偶元组，分区不变
     *             元组中第一个元素为Key，
     *             第二个元素是相同Key的Value集合 Iterable[]
     *             与reduceByKey的区别：
     *              reduceByKey会对Value进行聚合，而groupByKey只会将Value值放到一个集合中 RDD[(String, Iterable[Int])]
     *
     * groupBy: 与groupByKey的方法的区别是，
     *          groupBy是以自定义的值进行分组，而groupByKey固定以Key值进行分组
     *          groupBy分组后的value值为  RDD[(String, Iterable[(String, Int)])]   集合中为(key,value)的集合
     *          groupByKey分组后的value值为  RDD[(String, Iterable[Int])]   集合中均为value
     */
    val groupRDD: RDD[(String, Iterable[Int])] = kvRDD.groupByKey()
    groupRDD.collect().foreach(println)

    val gRDD: RDD[(String, Iterable[(String, Int)])] = kvRDD.groupBy(_._1)
    gRDD.collect().foreach(println)

    /**
     * aggregateByKey: 将数据根据不同的规则进行分区内计算和分区间计算
     *  aggregateByKey存在函数柯里化，有俩个参数列表
     *  第一个参数：需要传递一个参数，表示初始值，主要用于和第一个Key对应的Value进行比较，不然第一个Key的Value没办法比较
     *                  (设置需要注意，会出现问题，例如分区中所有值比设置的值小)
     *  第二个参数需要传递俩个参数：
     *          第一个参数表示分区内的计算规则
     *          第二个参数表示分区间的计算规则
     */
    val kv1RDD: RDD[(String, Int)] = sc.makeRDD(List(
          ("a", 1), ("a", 2), ("b", 3), ("b", 4),("b",5),("a",6)
      ),2)
    val aggregateByKeyRDD: RDD[(String, Int)] = kv1RDD.aggregateByKey(0)(
      (x, y) => math.max(x, y),
      (x, y) => x + y
    )
//    aggregateByKey最终的返回数据结果应该和初始值的类型保持一致
    aggregateByKeyRDD.collect().foreach(println)

//    如果初始值为String，返回类型也是String   RDD[(String, String)]
    val stRDD: RDD[(String, String)] = kv1RDD.aggregateByKey("")(_ + _, _ + _)
    /**
     * 如果分区内和分区间计算规则相同，Spark提供了简化的方法：foldByKey
     */
    kv1RDD.foldByKey(0)(_+_).collect().foreach(println)


    /**
     * aggregateByKey练习题： 计算每个Key对应的平均值
     */
    val newRDD: RDD[(String, (Int, Int))] = kv1RDD.aggregateByKey((0, 0))(
      (t, v) => {
        (t._1 + v, t._2 + 1)
      },
      (t1, t2) => {
        (t1._1 + t2._1, t1._2 + t2._2)
      }
    )

    val avgRDD: RDD[(String, Int)] = newRDD.mapValues {
      case (num, cnt) => {
        num / cnt
      }
    }
    avgRDD.collect().foreach(println)

    /**
     *  aggregateByKey设置初始值的方法其实并不是很合理，应该用第一个元素转化成初始值，这样才是一个合理的解决方案
     *  所以，Spark提供了combineByKey算子
     *  combineByKey :
     *      需要三个参数：
     *        第一个参数表示：将相同Key的第一个数据进行结构转换(例如：1 => (1,1) )，实现操作
     *        第二个参数表示：分区内的计算规则
     *        第三个参数表示：分区间的计算规则
     */
    val kv2RDD: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4),("b",5),("a",6)
    ),2)
    val combineRDD: RDD[(String, (Int, Int))] = kv2RDD.combineByKey(
      v => (v, 1),
      (t: (Int, Int), v) => {
        (t._1 + v, t._2 + 1)
      },
      (t1: (Int, Int), t2: (Int, Int)) => {
        (t1._1 + t2._1, t1._2 + t2._2)
      }
    )
    combineRDD.mapValues{
      case (num, cnt) => {
        num / cnt
      }
    }.collect().foreach(println)

    /**
      总结:
        reduceByKey底层实现:
               combineByKeyWithClassTag[V](
                  (v: V) => v,  直接将第一个元素拿过来，第一个值不参与计算
                  func,     分区内计算规则
                  func,     分区间计算规则   俩者完全相同
                  partitioner)

        foldByKey底层实现:
             combineByKeyWithClassTag[V](
                (v: V) => cleanedFunc(createZero(), v),   初始值和第一个key的Value值进行的分区内数据操作
                cleanedFunc,  分区内计算规则  俩者相同
                cleanedFunc,  分区间计算规则
                partitioner)

        aggregateByKey底层实现:
             combineByKeyWithClassTag[U](
                (v: V) => cleanedSeqOp(createZero(), v),  初始值和第一个key的Value值进行的分区内数据操作
                cleanedSeqOp,  分区内计算规则
                combOp,        分区间计算规则
                partitioner)


        combineByKey底层实现:
             combineByKeyWithClassTag(
                createCombiner,   初始值和第一个key的Value值进行的分区内数据操作
                mergeValue,     分区内计算规则
                mergeCombiners, 分区间计算规则
                partitioner, mapSideCombine, serializer)(null)
     */

    kv2RDD.reduceByKey(_+_)
//    需要自定义分区内和分区间计算规则时，使用aggregateByKey
    kv2RDD.aggregateByKey(0)(_+_,_+_)
//    分区内计算规则和分区间计算规则相同，则用foldByKey
    kv2RDD.foldByKey(0)(_+_)
//    combineByKey算法是aggregateByKey算子的优化版本
    kv2RDD.combineByKey(v => v,(x:Int,y:Int) => x+y,(x:Int,y:Int) => x+y)

    sc.stop()
  }
}
