package SparkSql.day01

import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Aggregator

object SparkSql_UDAFNewAPI {
  def main(args: Array[String]): Unit = {

//    TODO 创建SparkSQL的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql_Basic")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._
//    隐式转换在创建环境时导入,防止遗忘
//    TODO 执行逻辑操作
    val df: DataFrame = spark.read.json("Spark-core/datas/user.json")
    df.createOrReplaceTempView("user")
//    使用自定义函数类, 使用强类型函数需要  functions.udaf()
    spark.udf.register("avgAge",functions.udaf(new AvgUDF1()))

    /**
     * Spark早期版本中，不能在sql中使用强类型UDAF操作
     * SQL & DSL
     * 早期的UDAF强类型聚合函数使用 DSL 语法操作
     */
    val ds: Dataset[User] = df.as[User]
//    将UDAF函数转换为查询的列对象
    val udafCol: TypedColumn[User, Long] = new avgUDF().toColumn
//    将聚合函数的结果当成一个查询的列
    ds.select(udafCol).show()

    spark.sql("select avgAge(age) from user").show()
//    TODO 关闭环境
    spark.close()

  }
  /*
    自定义聚合函数类: 计算年龄平均值
      1. 继承Aggregator, 定义泛型
          IN:  输入数据类型 Long
          BUF: 缓冲区数据类型 Buff
          OUT: 输出数据类型 Long
      2. 重写方法(6个)
   */
  case class User(username:String,age:Long)
  case class Buff(var total : Long, var Count: Long)

//  Spark3.0使用强类型UDAF函数方法
  class AvgUDF1 extends Aggregator[Long,Buff,Long] {
//    初始化，scala中zero一般都是代表初始化
    override def zero: Buff = {
      Buff(0L,0L)
    }
//    聚合
    override def reduce(b: Buff, a: Long): Buff = {
      b.total =b.total +  a
      b.Count =b.Count +  1
      b
    }
//    合并Buff
    override def merge(b1: Buff, b2: Buff): Buff = {
      b1.Count =b1.Count + b2.Count
      b1.total =b1.total + b2.total
      b1
    }
//    计算结果
    override def finish(reduction: Buff): Long = {
      reduction.total/reduction.Count
    }
//    缓冲区的编码操作
    override def bufferEncoder: Encoder[Buff] = Encoders.product
//      输出的编码操作
    override def outputEncoder: Encoder[Long] = Encoders.scalaLong
  }

//  Spark3.0前无法使用强类型
class avgUDF extends Aggregator[User,Buff,Long] {
  //    初始化，scala中zero一般都是代表初始化
  override def zero: Buff = {
    Buff(0L,0L)
  }
  //    聚合
  override def reduce(b: Buff, a: User): Buff = {
    b.total =b.total +  a.age
    b.Count =b.Count +  1
    b
  }
  //    合并Buff
  override def merge(b1: Buff, b2: Buff): Buff = {
    b1.Count =b1.Count + b2.Count
    b1.total =b1.total + b2.total
    b1
  }
  //    计算结果
  override def finish(reduction: Buff): Long = {
    reduction.total/reduction.Count
  }
  //    缓冲区的编码操作
  override def bufferEncoder: Encoder[Buff] = Encoders.product
  //      输出的编码操作
  override def outputEncoder: Encoder[Long] = Encoders.scalaLong
}




}
