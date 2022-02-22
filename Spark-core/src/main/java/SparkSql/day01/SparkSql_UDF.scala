package SparkSql.day01

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkSql_UDF {
  def main(args: Array[String]): Unit = {

//    TODO 创建SparkSQL的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql_Basic")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
//    隐式转换在创建环境时导入,防止遗忘
    import spark.implicits._
//    TODO 执行逻辑操作
    val df: DataFrame = spark.read.json("Spark-core/datas/user.json")
//    用户自定义函数
    df.createOrReplaceTempView("user")

    spark.udf.register("prefixName", (name: String) =>{
      "Name: " + name
    })

    spark.sql("select age, prefixName(username) from user").show()
//    TODO 关闭环境
    spark.close()

  }

}
