package SparkSql.day01

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object SparkSql_UDAF {
  def main(args: Array[String]): Unit = {

//    TODO 创建SparkSQL的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql_Basic")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
//    隐式转换在创建环境时导入,防止遗忘
//    TODO 执行逻辑操作
    val df: DataFrame = spark.read.json("Spark-core/datas/user.json")
//    用户自定义函数
    df.createOrReplaceTempView("user")

    spark.udf.register("prefixName", (name: String) =>{
      "Name: " + name
    })

//    使用自定义函数类
    spark.udf.register("avgAge",new AvgUDF())
    spark.sql("select age, prefixName(username) from user").show()
    spark.sql("select avgAge(age) from user").show()
//    TODO 关闭环境
    spark.close()

  }
  /*
    自定义聚合函数类: 计算年龄平均值
      1. 继承UserDefinedAggregateFunction
      2. 重写方法

   */
  class AvgUDF extends UserDefinedAggregateFunction {
//    输入结构
    override def inputSchema: StructType = {
      StructType(
        Array(
          StructField("agg",LongType)
        )
      )
    }
//    缓冲区数据的结构
    override def bufferSchema: StructType = {
      StructType(
        Array(
          StructField("total",LongType),
          StructField("count",LongType)
        )
      )
    }
//    函数计算结果(输出)的数据类型
    override def dataType: DataType = LongType
//    函数的稳定性
    override def deterministic: Boolean = true
//    缓冲区初始化
    override def initialize(buffer: MutableAggregationBuffer): Unit = {
//      buffer(0) = 0L
//      buffer(1) = 0L
      buffer.update(0,0L)
      buffer.update(1,0L)
    }
//    根据输入的值进行更新buffer
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      buffer.update(0,buffer.getLong(0) + input.getLong(0))
      buffer.update(1,buffer.getLong(1) + 1)
    }
//    合并俩个buffer
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1.update(0,buffer1.getLong(0) + buffer2.getLong(0))
      buffer1.update(1,buffer1.getLong(1) + buffer2.getLong(1))
    }
//    计算
    override def evaluate(buffer: Row)= {
      buffer.getLong(0)/buffer.getLong(1)
    }
  }

}
