package SparkCore.SparkCore_Req

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object HotCategoryTop10_Optimize2 {
  def main(args: Array[String]): Unit = {
    /**
     * TODO 优化
     *  仍然存在Shuffle操作(reduceByKey),影响性能  => 使用累加器, 减少reduceByKey操作
     *
     */
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10_Optimize"))

    //    1.读取日志数据
    val actionRDD: RDD[String] = sc.textFile("Spark-core/datas/user_visit_action.txt")

    val accumulator = new HotCategoryAccumulator()
    sc.register(accumulator,"HotCategory")

    actionRDD.foreach{
      action => {
        val datas: Array[String] = action.split("_")
        if (datas(6) != "-1") {
          // 点击场合
          accumulator.add((datas(6),"click"))
        } else if (datas(8) != "null") {
          // 下单场合
          val id: Array[String] = datas(8).split(",")
          id.foreach(
            id => {
              accumulator.add(id, "order")
            }
          )
        } else if (datas(10) != "null") {
          //          支付场合
          val id: Array[String] = datas(10).split(",")
          id.foreach(
            id => {
              accumulator.add(id, "pay")
            }
          )
        }
      }
    }
    //    5.将品类进行排序，并取前10（点击数量 > 下单数量 > 支付数量）
    val accResult: mutable.Map[String, HotCategory] = accumulator.value
    val categories: mutable.Iterable[HotCategory] = accResult.map(_._2)
    val hotCategories: List[HotCategory] = categories.toList.sortWith(
      (left, right) => {
        if (left.clickCnt > right.clickCnt) {
          true
        } else if (left.clickCnt == right.clickCnt) {
          if (left.orderCnt > left.orderCnt) {
            true
          } else if (left.orderCnt == left.orderCnt) {
            if (left.payCnt > left.payCnt) {
              true
            } else {
              false
            }
          } else {
            false
          }
        } else {
          false
        }
      }
    )

    //    6.将结果打印出来
    hotCategories.foreach(println)

    sc.stop()

  }
  case class HotCategory(cid: String, var clickCnt: Int, var orderCnt: Int, var payCnt: Int){

  }
  /**
   * TODO 自定义累加器
   *  1. 继承AccumulatorV2[IN,OUT] ,定义泛型
   *    IN:  (品类ID, 行为类型)
   *    OUT: mutable.Map[String, HotCategory]
   *  2. 重写方法(6个)
   */
  class HotCategoryAccumulator extends AccumulatorV2[(String, String), mutable.Map[String, HotCategory]]{
    private val hcMap = mutable.Map[String, HotCategory]()

    override def isZero: Boolean = hcMap.isEmpty

    override def copy(): AccumulatorV2[(String, String), mutable.Map[String, HotCategory]] = new HotCategoryAccumulator()

    override def reset(): Unit = hcMap.clear()

    override def add(v: (String, String)): Unit = {
      val cid = v._1
      val actionType = v._2

      val category: HotCategory = hcMap.getOrElse(cid, new HotCategory(cid, 0, 0, 0))
      if (actionType == "click"){
        category.clickCnt += 1
      }else if (actionType == "order"){
        category.orderCnt += 1
      }else if (actionType == "pay"){
        category.payCnt += 1
      }
      hcMap.update(cid, category)
    }

    override def merge(other: AccumulatorV2[(String, String), mutable.Map[String, HotCategory]]): Unit = {
      val map1 = this.hcMap
      val map2 = other.value

      map2.foreach{
        case (cid, hc) => {
          val category: HotCategory = map1.getOrElse(cid, new HotCategory(cid, 0, 0, 0))
          category.clickCnt += hc.clickCnt
          category.orderCnt += hc.orderCnt
          category.payCnt += hc.payCnt
          map1.update(cid, category)
        }
      }
    }

    override def value: mutable.Map[String, HotCategory] = hcMap
  }
}
