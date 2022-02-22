package SparkCore.Spark07_ACC

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}


/**
 * 累加器：分布式 共享 只写 变量
 *
 */
object Spark_ACC {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("RDD_Persist"))

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)
//    reduce: 分区内计算，分区间计算
    val i: Int = rdd.reduce(_ + _)
    println(i)

    var sum = 0

    rdd.foreach{
      num => {
        sum += num
      }
    }
//    结果发现，sum为0，因为sum=0传给Executor，Executor运算后并没有把sum的值传回Driver，所以依然是0
    println(sum)

//    因此，就要用到了累加器
//    获取系统累加器, spark默认就提供了简单数据聚合的累加器
    val sumAcc: LongAccumulator = sc.longAccumulator("sum")

//    转换算子中调用累加器，如果没有行动算子就不会执行，如果多执行行动算子，就会执行多次，造成多次累加
    rdd.foreach(
//      累加
      num => sumAcc.add(num)
    )
//    LongAccumulator(id: 50, name: Some(sum), value: 10)
//    获取累加器的值
    println(sumAcc)

    sc.stop()
  }
}
