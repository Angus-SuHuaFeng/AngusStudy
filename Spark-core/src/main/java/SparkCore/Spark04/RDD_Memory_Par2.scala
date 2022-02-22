package SparkCore.Spark04

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Memory_Par2 {
  def main(args: Array[String]): Unit = {
//    TODO 准备环境
    val rDD_Memory_ParConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_Memory_Par")
    val sc = new SparkContext(rDD_Memory_ParConf)
//    TODO 创建RDD
//    RDD的并行度 & 分区
    /**
     * 1.分区存储问题(分区后怎么存储? 确定了数据和分区数后怎么将数据分配到每个分区呢?)
     *    我们还是通过源码进行理解, 依然是点击makeRDD
     *  发现parallelize(seq, numSlices),其中seq中是我们的数据,numSlices为分区数,所以它和分区有关,点进去看
     *  发现 new ParallelCollectionRDD[T](this, seq, numSlices, Map[Int, Seq[String] ]()) 新建了一个RDD, 再点进去
     *  发现有个getPartitions 方法,
     *  override def getPartitions: Array[Partition] = {
          val slices = ParallelCollectionRDD.slice(data, numSlices).toArray
          slices.indices.map(i => new ParallelCollectionPartition(id, i, slices(i))).toArray
        }
     * slice(data, numSlices)明显是我们要找的实现分区的函数,点进去这里就是实现分区的源码了
     * 我们发现, 先是判断分区数,小于1明显不合适
     * 发现有一个positions方法, 下方有的seq的模式匹配  seq match
     * 1. 匹配Range类型
     * 2. 匹配NumericRange类型
     * 3. _ 其他所有情况
     *   这里用到了positions函数:
     *      positions(length: Long, numSlices: Int)
     *      需要传递的参数有 数据的长度和分区数,可见是通过这俩个值进行分区
     * def positions(length: Long, numSlices: Int): Iterator[(Int, Int)] = {
          (0 until numSlices).iterator.map { i =>
            val start = ((i * length) / numSlices).toInt
            val end = (((i + 1) * length) / numSlices).toInt
            (start, end)
          }
        }
      until 表示左闭右开
     *  函数返回的是一个包含 起始位置和结束位置的元组 的迭代器 (),(),()
     *  例如:
     *  List(1,2,3,4,5)   length = 5    numSlices = 3
     *  接下来进行分区:                                                分区区间 索引
     *    0 => start = 0            end = 1*5/3 = 1   所以第一个分区为 (0,1) 即 0
     *    1 => start = 1*5/3 = 1    end = 2*5/3 = 3   所以第二个分区为 (1,3) 即 1,2
     *    2 => start = 2*5/3 = 3    end = 3*5/3 = 5   所以第三个分区为 (3,5) 即 3,4
     *   计算完每个分区后会将(start,end)返回到 Iterator[(Int, Int)] 类型的迭代器中
     *  4. 通过positions函数计算出的分区区间将数据分开, 这样就分好了
     *    array.slice(start, end).toSeq   下方slice函数中参数为from, until 表明从from到until,并且不包含右侧区间
     *    def slice(from: Int, until: Int): Repr = ???
     */
    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),3)
    rdd1.saveAsTextFile("Spark-core/output1")

    //  TODO 关闭环境
    sc.stop()

  }
}
