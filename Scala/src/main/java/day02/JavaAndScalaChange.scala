package day02

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class JavaAndScalaChange {

}
/**
 *  Java类型和Scala类型的相互转换
 */
object JavaAndScalaChange{
  def main(args: Array[String]): Unit = {
    val arr1 = ArrayBuffer("1","2")
    //    Scala to Java   (在redis中使用map就需要导包)
    import scala.collection.JavaConversions._
    val builder = new ProcessBuilder(arr1)
    println(arr1)
    println(builder.command())

    //    Java to Scala
    val strings = builder.command()
    //    结果为[1, 2] 仍然是Java的类型
    println(strings)
    //    结果是 ArrayBuffer(1, 2) 成功转换成Scala类型，因此，java to scala 需要强制转换
    val strings1: mutable.Buffer[String] = builder.command()
    println(strings1)
  }
}
