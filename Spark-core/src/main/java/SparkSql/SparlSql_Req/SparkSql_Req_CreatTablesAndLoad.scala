package SparkSql.SparlSql_Req

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSql_Req_CreatTablesAndLoad extends App {
//  设置权限,不然无法访问Hadoop
  System.setProperty("HADOOP_USER_NAME", "root")
  //TODO 创建SparkSql的运行环境
  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql_Hive")
  private val spark: SparkSession = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()

//  TODO 使用SparkSql连接外置的Hive
  spark.sql("use angus")
//  创建user_visit_action表
  spark.sql(
    """
      |CREATE TABLE `user_visit_action`(
      | `date` string,
      | `user_id` bigint,
      | `session_id` string,
      | `page_id` bigint,
      | `action_time` string,
      | `search_keyword` string,
      | `click_category_id` bigint,
      | `click_product_id` bigint,
      | `order_category_ids` string,
      | `order_product_ids` string,
      | `pay_category_ids` string,
      | `pay_product_ids` string,
      | `city_id` bigint)
      |row format delimited fields terminated by '\t';
      |""".stripMargin
  )
//  将数据导入到user_visit_action表中
  spark.sql(
    "load data local inpath 'Spark-core/datas/user_visit_action.txt' into table user_visit_action"
  )
//  创建product_info表
  spark.sql(
    """
      |CREATE TABLE `product_info`(
      | `product_id` bigint,
      | `product_name` string,
      | `extend_info` string)
      |row format delimited fields terminated by '\t'
      |""".stripMargin
  )
  spark.sql(
    """
      |load data local inpath 'Spark-core/datas/product_info.txt' into table product_info
      |""".stripMargin
  )
//  创建city_info表
  spark.sql(
    """
      |CREATE TABLE `city_info`(
      | `city_id` bigint,
      | `city_name` string,
      | `area` string)
      |row format delimited fields terminated by '\t'
      |""".stripMargin
  )
//  将数据导入到city_info表中
  spark.sql(
    """
      |load data local inpath 'Spark-core/datas/city_info.txt' into table city_info
      |""".stripMargin
  )

//  查询数据
  spark.sql("select * from city_info").show()
//  TODO 关闭环境
  spark.close()
}
