package SparkSql.day02

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSql_Hive extends App {
  //TODO 创建SparkSql的运行环境
  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql_Hive")
  private val spark: SparkSession =   SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()

//  TODO 使用SparkSql连接外置的Hive
  /*
      1.拷贝hive-site.xml文件到resource下
      2.启动hive支持enableHiveSupport()、
      3.增加对应的依赖关系(包含MySql驱动)
   */
  spark.sql("show tables").show()

//  TODO 关闭环境
  spark.close()
}
