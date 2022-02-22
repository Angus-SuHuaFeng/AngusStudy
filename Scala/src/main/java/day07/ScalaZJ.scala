package day07

import scala.beans.BeanProperty


/**
 *  注解: 可以为类，方法，字段局部变量，参数，表达式，类型参数以及各种类型定义添加注解
 *
 */
//构造器注解，需要在主构造器之前，类名之后，且需要加括号，如果注解有参数，则写 在注解括号里  @Inject()
class ScalaZJ (var name: String , var age: Int){
//  自动引入get set方法
  @BeanProperty var username = ""
}

/**
 * 注解实现: 以实现自己的注解必须扩展 Annotation 特质
 */
class unchecked extends annotation.Annotation{
  @unchecked val name = ""
}

/**
 * 用于优化的注解:
 * 尽管 Scala 编译器会尝试使用尾递归优化，但有时候某些不太明显的原因会造成它无法这样做。
 * 如果你想编译器无法进行优化时报错，则应该给你的方法加上@tailrec 注解。
 */

//尾递归
object Test1 extends App{
  def story(): Unit = {
    println("从前有座山，山上有座庙，庙里有个老和尚，一天老和尚给小和尚讲故事")
    story()
  }
  story()
}