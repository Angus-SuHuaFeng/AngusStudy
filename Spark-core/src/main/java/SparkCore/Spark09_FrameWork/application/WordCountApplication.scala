package SparkCore.Spark09_FrameWork.application

import SparkCore.Spark09_FrameWork.common.SparkApplication
import SparkCore.Spark09_FrameWork.controller.WordCountController

object WordCountApplication extends App with SparkApplication{

  private val controller = new WordCountController()

  init(){
    controller.dispatch()
  }

}