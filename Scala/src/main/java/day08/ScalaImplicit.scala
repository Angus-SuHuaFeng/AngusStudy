package day08

import java.io.File
import scala.io.Source

/**
 * 1. 隐式转换函数是以 implicit 关键字声明的带有单个参数的函数。这种函数将会自动应用，将值从一种类型转换为另一种类型。
 */
class ScalaImplicit {

}

object Test1 extends App{
//  隐式转换, 凡是符合对应类型的变量都会被转换
  implicit def app(d: Double): Int = d.toInt
  val a: Int = 3.5
  println(a)
}

/**
 * 在 Scala 中,如果你想标记某一个泛型可以隐式的转换为另一个泛型，可以使用：[T <% Comparable[T] ]
 * 由于 Scala 的 Int 类型没有实现 Comparable 接口，所以我们需要将 Int 类型隐式的转换为 RichInt
 */
class Pair[T <% Comparable[T]](val first: T, val second: T) {
  def smaller = if (first.compareTo(second) < 0) first else second
  override def toString = "(" + first + "," + second + ")"
}

object Test2 extends App {
  val p = new Pair(4, 2)
  println(p.smaller)
}

/**
 * 2. 利用隐式转换丰富类库功能
 *  如果需要为一个类增加一个方法，可以通过隐式转换来实现。比如想为 File 增加一个 read 方法
 */
class RichFile(val from:File){
  def read = Source.fromFile(from.getPath).mkString
}


object Test3 extends App {
  implicit def file2RichFile(from: File) = new RichFile(from)

  private val file : RichFile = new File("E:\\IDEADemo\\AngusStudy\\Scala\\src\\main\\java\\day08\\test")
  println(file.read)
}

/**
 * 3. 隐式值:
 */
object Test4 extends App{
  implicit val name = "Nick"
  def person(implicit name: String) = name
  println(person)
}

/**
 * 4. 隐式视图
 */


/**
 * 5. 隐式类
 *   在 scala2.10 后提供了隐式类，可以使用 implicit 声明类，但是需要注意以下几点：
 *   -- 其所带的构造参数有且只能有一个
 *   -- 隐式类必须被定义在“类”或“伴生对象”或“包对象”里
 *   -- 隐式类不能是 case class（case class 在定义会自动生成伴生对象与 2 矛盾）
 *   -- 作用域内不能有与之相同名称的标示符
 */
object StringUtils extends App {
  implicit class StringImProvement(val s: String) {
    def increment: String = s.map(x => (x + 1).toChar)
  }

  println("a".increment)
}


