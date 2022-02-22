package day02

import scala.collection.SeqView

class ScalaView {

}

/**
 * View
 *
 */
object ScalaView extends App{
  private val value: SeqView[Int, Seq[_]] = (1 to 1000).view.map(x => x).filter(
    x =>
      x.toString == x.toString.reverse
  )
//  俩种遍历方式
  println(value.mkString(","))

  for (elem <- value) {
    print(elem+",")
  }
  println()
  /**
   * 并行执行par
   */
//  012345
  (0 to 5).foreach(print)
  println()
//  051423    不是按照顺序的,是多个core(多线程)并行打印
  (0 to 5).par.foreach(print)
  println()
//  查看并行集合中元素访问的线程
  val result1 = (0 to 10).map{case _ => Thread.currentThread.getName}.distinct
  val result2 = (0 to 10).par.map{case _ => Thread.currentThread.getName}.distinct
  println(result1)
  println(result2)

  /**
   * 操作符:
   *  1. ` ` 着重操作符: 使用保留字
   *  2. 前置操作符    +A  ==> A.unary_+
   *  3. 中置操作符    A+B ==> A.+(B)
   *  4. 后置操作符    A+  ==> A.+
   */

}
