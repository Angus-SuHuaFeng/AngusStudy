package SparkStreaming.day01

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext, StreamingContextState}


/**
 *  优雅地关闭: 流式任务需要 7*24 小时执行，但是有时涉及到升级代码需要主动停止程序，但是分
    布式程序，没办法做到一个个进程去杀死，所有配置优雅的关闭就显得至关重要了。
    使用外部文件系统来控制内部程序关闭。
 */
object SparkStreaming_Close {
  def main(args: Array[String]): Unit = {

//    TODO 数据恢复
//    getActiveOrCreate() ：
//        第一个参数为：检查点路径
//        第二个参数为：如果从检查点获取数据失败，重新创建StreamingContext
    val ssc: StreamingContext = StreamingContext.getActiveOrCreate("Spark-core/checkpoint", () => {
      val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming_Window")

      val ssc = new StreamingContext(sparkConf, Seconds(3))

      //   TODO 计算逻辑
      val line: ReceiverInputDStream[String] = ssc.socketTextStream("BigData1", 9991)

      val wordToOne: DStream[(String, Int)] = line.map((_, 1))

      //    reduceByKeyAndWindow: 当窗口的范围比较大，但是滑动的步长比较小，那么可以采用增加数据和删除数据的方式
      //    无需重复计算，提升性能
      val wordToCountDS: DStream[(String, Int)] = wordToOne.reduceByKeyAndWindow(
        (x: Int, y: Int) => {
          x + y
        },    // 加上新进入窗口的批次中的元素
        (x: Int, y: Int) => {
          x - y
        },    // 移除离开窗口的老批次元素
        Seconds(9), // 窗口时长
        Seconds(3)  // 窗口步长
      )
      wordToCountDS.print()
      ssc
    })

    ssc.checkpoint("Spark-core/checkpoint")



    ssc.start()
//    TODO 优雅关闭
    /*
       如果想要关闭采集器，那么需要创建新的线程（独立于主线程，因为ssc.awaitTermination()，主线程会处于阻塞状态）
       这里我们可以直接new 一个Thread ，也可以写一个线程类去关闭
     */
    new Thread(new StopSparkStreaming(ssc)).start()
    ssc.awaitTermination()

    ssc.stop()
  }
  class StopSparkStreaming (ssc: StreamingContext) extends Runnable{
    /*
        优雅地关闭: 计算节点不再继续接收新的数据, 而是将现有的数据处理完毕再关闭
        需要使用第三方程序进行关闭:
          MySql: Table(stopSpark)  => Row => data
          Redis: Data(K-V)
          ZK   : /stopSpark
          HDFS : /stopSpark
        可以使用外部文件系统或数据库作为关闭程序的触发条件
     */
    override def run(): Unit = {
      val conf = new Configuration()
      conf.set("fs.defaultFS","hdfs://BigData1:8020")
      val fs: FileSystem = FileSystem.get(conf)

      while (true) {
        try
          Thread.sleep(5000)
        catch {
          case e: InterruptedException =>
            e.printStackTrace()
        }
//        判断sparkContext是否运行中
        val state: StreamingContextState = ssc.getState()
//        根据HDFS下是否有/stopSpark目录进行判断
        val bool: Boolean = fs.exists(new Path("hdfs://BigData1:8020/stopSpark"))
        if (bool) {
          if (state == StreamingContextState.ACTIVE) {
            ssc.stop(true,true)
            System.exit(0)
          }
        }
      }
    }
  }
}
