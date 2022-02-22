package SparkSql.day01

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object SparkSql_Basic {
  def main(args: Array[String]): Unit = {

//    TODO 创建SparkSQL的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql_Basic")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
//    隐式转换在创建环境时导入,防止遗忘
    import spark.implicits._
//    TODO 执行逻辑操作

//    DataFrame
    val df: DataFrame = spark.read.json("Spark-core/datas/user.json")
//    df.show()

//    DataFrame => sql
    df.createOrReplaceTempView("user")
    spark.sql("select * from user").show()

//    DataFrame => DSL
    df.select("age","username").show()
//    如果涉及到转换操作，需要导入 import spark.implicits._  , 这里的spark不是包名，而是环境对象spark
    import spark.implicits._
    df.select($"age" + 1).show()
    df.select('age + 1).show()

//    DataSet
//    DataFrame其实是特定泛型的DataSet
    val seq = Seq(1,2,3,4)
    val ds: Dataset[Int] = seq.toDS()
    ds.show()

//    RDD <=> DataFrame
    val rdd = spark.sparkContext.makeRDD(List(
      (1,"zs",30),
      (2,"ls",20),
      (3,"ww",40)
    ))
    val dataFrame: DataFrame = rdd.toDF("id", "name", "age")
    dataFrame.collect().foreach(println)
//    DataFrame => RDD
    val rowRDD: RDD[Row] = df.rdd
//    DataFrame <=> DataSet
    val ds1: Dataset[User] = df.as[User]
    val frame: DataFrame = ds1.toDF()
//    RDD <=> DataSet
    val ds2: Dataset[User] = rdd.map {
      case (id, name, age) => {
        User(id, name, age)
      }
    }.toDS()

    val userRDD: RDD[User] = ds2.rdd
//    TODO 关闭环境
    spark.close()

  }
  case class User (id: Int, name :String,age:Int)
}
