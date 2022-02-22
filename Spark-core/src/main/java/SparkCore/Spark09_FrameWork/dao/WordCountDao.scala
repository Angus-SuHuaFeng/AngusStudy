package SparkCore.Spark09_FrameWork.dao

import SparkCore.Spark09_FrameWork.util.EnvUtil

// 持久层
class WordCountDao {

  def readFile(path: String) ={
    EnvUtil.take().textFile(path)
  }

}
