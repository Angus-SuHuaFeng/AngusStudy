/**
 * 对于 package，有如下几种形式：
 * 第一种
 */
//package day06.packagetest
//
//class ScalaPackage {
//
//}

/**
 * 第二种
 */
package day06

package packagetest
class ScalaPackage {

}

/**
 * 第三种
 */
package day06{
  package packagetest{
    class ScalaPackage{

    }
  }
}

/**
 * 1、包也可以像内部类那样嵌套，作用域原则：可以直接向上访问。即，子 package 中直接 访问父 package 中的内容。(即：作用域)
 * 2、包对象可以持有函数和变量
 * 3、引入语句可以引入包、类和对象
 * 4、源文件的目录和包之间并没有强制的关联关系
 * 5、可以在同一个.scala 文件中，声明多个并列的 package
 * 6、包名可以相对也可以绝对，比如，访问 ArrayBuffer 的绝对路径是： _root_.scala.collection.mutable.ArrayBuff
 */

/**
 * 包对象：
 * 包可以包含类、对象和特质 trait，但不能包含函数或变量的定义。很不幸，这是 Java 虚拟 机的局限。
 * 把工具函数或常量添加到包而不是某个 Utils 对象，这是更加合理的做法。
 * 包对象的出现正是为了解决这个局限。每个包都可以有一个包对象。你需要在父包中定义它，且名称与子包一样。
 */
package object ScalaPackage {
  val defaultName = "Nick"
  var price = 0
  def increase = {
    price += 1
    price
  }


}

package ScalaPackage {
  class Person {
//    从包对象拿到的常置, []可以设置包内代码的可见性，如ScalaPackage，则只能本包可见，如为day06，则父包也可见
    private[day06] var name = defaultName
  }

  object Person extends App{
    println(defaultName)
//    包对象中的函数
    println(increase)
  }
}

/**
 * 重命名: 如果你想要引人包中的几个成员，可以像这样使用选取器( selector)，而且选取的同时，可以重命名：
 */
import java.util.{HashMap => JavaHashMap}
import scala.collection.mutable._
object Test extends App{
//  JavaHashMap
}

/**
 * 隐藏：选取器 HashMap =>_将隐藏某个成员而不是重命名它。这仅在你需要引入其他成员时有用：
 */
//这样就可以将java.util.HashMap隐藏起来
import java.util.{HashMap => _, _}
import scala.collection.mutable._
