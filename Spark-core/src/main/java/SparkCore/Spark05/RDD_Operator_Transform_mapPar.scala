package SparkCore.Spark05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD_Operator_Transform_mapPar {
  def main(args: Array[String]): Unit = {
//    TODO 创建Spark环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD_Operator_Transform")
    val sc = new SparkContext(sparkConf)
//    TODO 业务代码  算子 - mapPartitions

    /**
     *  mapPartitions: 可以以分区为单位进行数据转换操作
     *                 但是会将整个分区的数据加载到内存中进行引用
     *                 处理完的数据不会被释放掉，存在对象的引用
     *                 （例如100G的数据，已经处理了90个G，数据并没有全部处理完，已处理的数据不会释放，只有等待全部处理完才可以释放）
     *                 在内存较小，数据量较大的场合下，容易出现内存溢出（OOM）out of memory
     *
     * 所以map和mapPartitions各有各的用处，map适合在性能要求不高的，运行环境的内存不足的场景下使用
     * 而mapPartition是在内存充足的情况下以空间换时间，用内存来提高性能（可以理解为批处理，当然比一个一个处理快）
     *
     * map 和 mapPartitions 的区别？
         ➢ 数据处理角度
         Map 算子是分区内一个数据一个数据的执行，类似于串行操作。而 mapPartitions 算子
         是以分区为单位进行批处理操作。
         ➢ 功能的角度
         Map 算子主要目的将数据源中的数据进行转换和改变。但是不会减少或增多数据。
         MapPartitions 算子需要传递一个迭代器，返回一个迭代器，没有要求的元素的个数保持不变，
         所以可以增加或减少数据
         ➢ 性能的角度
         Map 算子因为类似于串行操作，所以性能比较低，而是 mapPartitions 算子类似于批处
         理，所以性能较高。但是 mapPartitions 算子会长时间占用内存，那么这样会导致内存可能
         不够用，出现内存溢出的错误。所以在内存有限的情况下，不推荐使用。使用 map 操作。
     */
    val numRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)

    val mapParRDD: RDD[Int] = numRDD.mapPartitions(
      iter => {
        println("---> ")
        iter.map(_ * 2)
      }
    )
    mapParRDD.collect().foreach(println)

//    TODO 关闭资源
    sc.stop()

  }
}
