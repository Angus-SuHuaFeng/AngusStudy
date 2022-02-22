package SparkCore.Spark06_RDD.operator.action

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object foreach_Operator {
  def main(args: Array[String]): Unit = {
    /**
     * foreach : 分布式遍历 RDD 中的每一个元素，调用指定函数
     */
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_ActionOperator"))

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    /**
     * 发现执行结果不一样
        1234  先collect再foreach: 将数据从Executor提取到Driver端，再进行print打印
        1324  直接foreach: 数据从Driver端发送到Executor后在Executor中打印

      从计算的角度, 算子以外的代码都是在 Driver 端执行, 算子里面的代码都是在 Executor端执行。
     */
    rdd.collect().foreach(print)
    println()
    rdd.foreach(print)

  }
}

object Test {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_ActionOperator"))

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

//    TODO 测试
    val user = new User()
//    Task not serializable
//    Caused by: java.io.NotSerializableException: Spark06_RDD.operator.action.Test$User
//    因为foreach算子中的计算实际上是一个匿名函数，会在Executor中执行，所以对象需要传输到Executor中，会有网络IO，因此需要序列化
//    闭包检查： 从异常错误中我们发现，时间上Job并没有执行，而是先对算子中闭包的对象进行序列化检查，如果没有序列化就会报错，不会继续执行
    /**
     * 源码中先进性序列化检查，func就是我们在算子中写的匿名函数
      if (checkSerializable) {
        ensureSerializable(func)
      }
     */
    rdd.foreach(
      num => println(user.age + num)
    )

  }
//  class User extends Serializable {
//  样例类在编译时，会自动混入序列化特质，（实现序列化接口）
  case class User(){
    val age : Int = 30
  }
}
