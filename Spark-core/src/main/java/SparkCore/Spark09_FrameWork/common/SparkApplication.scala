package SparkCore.Spark09_FrameWork.common

import SparkCore.Spark09_FrameWork.util.EnvUtil
import org.apache.spark.{SparkConf, SparkContext}

trait SparkApplication {

  def init(master: String = "local[*]",appName: String = "Application")(option : => Unit)={
    val sc = new SparkContext(new SparkConf().setMaster("local[*]").setAppName(appName))
    EnvUtil.put(sc)
    try {
      option
    }catch {
      case exception: Exception => println(exception.getMessage)
    }

    sc.stop()
    EnvUtil.clear()
  }
}
