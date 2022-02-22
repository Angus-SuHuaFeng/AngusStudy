package SparkCore.Spark09_FrameWork.controller

import SparkCore.Spark09_FrameWork.common.SparkController
import SparkCore.Spark09_FrameWork.service.WordCountServer


// 控制层
class WordCountController extends SparkController{

  private val wordCountService = new WordCountServer()

  override def dispatch() ={
    val array: Array[(String, Int)] = wordCountService.dataAnalysis()
    array.foreach(println)
  }
}
