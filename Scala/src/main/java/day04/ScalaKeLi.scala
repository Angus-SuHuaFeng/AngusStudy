package day04

class ScalaKeLi {

}

/**
 * 函数编程中，接受多个参数的函数都可以转化为接受单个参数的函数，这个转化过程就叫柯里化
 */
object ScalaKeLi extends App{
  /**
   * 比较两个字符串在忽略大小写的情况下是否相等，注意，这里是两个任务：
   * 1、全部转大写（或小写）
   * 2、比较是否相等
   * 针对这两个操作，我们用一个函数去处理的思想，其实无意间也变成了两个函数处理的思想。示例如下：
   */
  val a = Array("Hello", "World")
  val b = Array("hello", "world")
//  这里用了俩个函数，有俩个括号()()
  println(a.corresponds(b)(_.equalsIgnoreCase(_)))
}
