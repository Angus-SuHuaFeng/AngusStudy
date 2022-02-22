package day09

import scala.util.matching.Regex

/**
 * 正则: 我们可以通过正则表达式匹配一个句子中所有符合匹配的内容
 */
object ScalaRegex extends App {
//  俩种方式生成正则
  private val pattern1 = new Regex("(S|s)cala")
//  较常用
  val pattern2: Regex = "(S|s)cala".r

  val str = "Scala is scalable and cool"
  println((pattern2 findAllIn str).mkString(","))
}