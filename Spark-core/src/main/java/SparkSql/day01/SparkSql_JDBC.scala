package SparkSql.day01

import org.apache.spark.SparkConf
import org.apache.spark.sql._

object SparkSql_JDBC {
  def main(args: Array[String]): Unit = {

//    TODO 创建SparkSQL的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql_Basic")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._
//    TODO 读取MySql数据
    val df: DataFrame = spark.read
      .format("jdbc").option("url", "jdbc:mysql://BigData1:3306/study")
      .option("user", "root")
      .option("password", "000000")
      .option("dbtable", "staff")
      .load()
    df.show()

//    TODO 向MySql中写入数据
    val ds: Dataset[staff] = df.as[staff]
    ds.write.format("jdbc")
      .option("url", "jdbc:mysql://BigData1:3306/study")
      .option("user", "root")
      .option("password", "000000")
      .option("dbtable", "staff1")
      .mode(SaveMode.ErrorIfExists)
      .save()

//    TODO 关闭环境
    spark.close()

  }
  case class staff(id:Long, name:String,gender:String)
}
