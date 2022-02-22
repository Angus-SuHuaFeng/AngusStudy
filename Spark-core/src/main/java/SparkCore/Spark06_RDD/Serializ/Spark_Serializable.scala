package SparkCore.Spark06_RDD.Serializ

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Spark_Serializable {
  def main(args: Array[String]): Unit = {
    //1.创建 SparkConf 并设置 App 名称
    val conf: SparkConf = new
        SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")
    //2.创建 SparkContext，该对象是提交 Spark App 的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.创建一个 RDD
    val rdd: RDD[String] = sc.makeRDD(Array("hello world", "hello spark", "hive", "atguigu"))

    //3.1 创建一个 Search 对象
    val search = new Search("hello")

    //3.2 函数传递，打印：ERROR Task not serializable
    /**
     *  如果Search对象不序列化，会报错
     */
    search.getMatch1(rdd).collect().foreach(println)

    //3.3 属性传递，打印：ERROR Task not serializable
    search.getMatch2(rdd).collect().foreach(println)

    //4.关闭连接
    sc.stop()

  }

}

/**
 * 在Scala中，构造器中的参数如果被类方法使用就会自动转换成类的属性，如果没有被类方法使用则为形参
 *  Search类中，query被isMatch和getMatch2方法使用，所以在编译时，
 *  query对象会成为Search类的属性，因此RDD算子在调用query时会报错，需要序列化
 * @param query
 */
class Search(query:String) extends Serializable {
  def isMatch(s: String): Boolean = {
    s.contains(query)
  }

  // 函数序列化案例
  def getMatch1(rdd: RDD[String]): RDD[String] = {
    rdd.filter(isMatch)
  }

  // 属性序列化案例
  def getMatch2(rdd: RDD[String]): RDD[String] = {
    rdd.filter(x => x.contains(query))
  }
}
