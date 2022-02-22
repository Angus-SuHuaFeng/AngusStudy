package SparkCore.Spark06_RDD.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Key_value_join {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Key_Value_Functions")
    val sc = new SparkContext(sparkConf)

    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

    val rdd2: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6),("d",1),("a",3)
    ))

    val joinRDD: RDD[(String, (Int, Int))] = rdd1.join(rdd2)

    /**
     *  join: 俩个不同数据源的数据，相同的Key的Value会连接在一起，形成元组
     *        如果俩个数据源中的Key没有匹配上，那么数据就不会出现在结果中
     *        如果俩个数据源中Key有多个相同的，会依次匹配，可能会出现笛卡儿积，数据量会几何性增长，会导致性能降低，因此需要谨慎使用
     * 在类型为(K,V)和(K,W)的 RDD 上调用，返回一个相同 key 对应的所有元素连接在一起的(K,(V,W))的 RDD
     */
    joinRDD.collect().foreach(println)

    /**
     *  leftOuterJoin: 类似于 SQL 语句的左外连接, 如果另一个数据源中没有匹配项，则会返回None
     *    例如: (d,(1,None))
     *  rightOuterJoin: 类似于 SQL 语句的右外连接, 如果另一个数据源中没有匹配项，则会返回None
     *    例如: (d,(None,1))
     */
    val leftJoinRDD: RDD[(String, (Int, Option[Int]))] = rdd2.leftOuterJoin(rdd1)
    leftJoinRDD.collect().foreach(println)

    val rightJoinRDD: RDD[(String, (Option[Int], Int))] = rdd1.rightOuterJoin(rdd2)
    rightJoinRDD.collect().foreach(println)

    /**
     *  cogroup: connect + group    分组再连接
     *      在类型为(K,V)和(K,W)的 RDD 上调用，返回一个(K,(Iterable<V>,Iterable<W>))类型的 RDD
     *
     */
    val rdd3: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 2)
    ))

    val rdd4: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6)
    ))
    val cogroupRDD: RDD[(String, (Iterable[Int], Iterable[Int]))] = rdd3.cogroup(rdd4)
    cogroupRDD.collect().foreach(println)

    sc.stop()
  }
}
