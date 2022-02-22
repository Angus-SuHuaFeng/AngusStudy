package SparkStreaming.SparkStreaming_Req.Util

import java.io.{FileInputStream, InputStreamReader}
import java.util.Properties


object PropertiesUtil {
  def load(propertiesName:String): Properties ={
    val prop=new Properties()
    prop.load(new
        InputStreamReader(new FileInputStream(propertiesName) , "UTF-8"))
    prop
  }
}