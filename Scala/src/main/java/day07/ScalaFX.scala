package day07

import java.lang

class ScalaFX {

}
/**
 * 泛型：
 *  类和特质都可以带类型参数，用方括号来定义类型参数，可以用类型参数来定义变量、方法 参数和返回值。
 *  带有一个或多个类型参数的类是泛型类
 */
/**
 * 1. 泛型类
 * 如下，如果实例化时没有指定泛型类型，则 scala 会自动根据构造参数的类型自动推断泛型的具体类型。
 */

class Pair[S, T] (val first:S, val second: T){

}

/**
 * 2. 泛型函数: 函数或方法也可以有类型（泛型）参数。
 */
object FXMain extends App {
  def getMiddle[T](strings: Array[T]) = strings.foreach(println)
//  从参数类型来推断类型
  println(getMiddle(Array("Angus","had")).getClass.getTypeName)

  //指定类型，并保存为具体的匿名函数。
  val f = getMiddle[String] _

  f(Array("1"))
}

/**
 * 3. 上界:
 *    在 Java 泛型里表示某个泛型是另外一个泛型的子类型可以使用 extends 关键字, 而在 scala 中使用符号“<:”，这种形式称之为泛型的上界。
 *    A 是 B的父类:
        Java: <A extends B>
        Scala:[B <: A]
 *
 *    在 Java 中，T 同时是 A 和 B 的子类型，称之为多界，形式如：<T extends A & B>
 *    在 Scala 中，对上界和下界不能有多个，但是可以使用混合类型，如：[T <: A with B]
 */
// 传统方法
class ComparableInt(n1:Int, n2:Int) {
//  返回较大的值
  def greater: Int = if (n1 > n2) n1 else n2
}
// 上界用法  T <: Comparable[T] 表示T是一个可比较类型
class CommonCompare[T <: Comparable[T]] (obj1: T, obj2: T) {
  def greater: T = if (obj1.compareTo(obj2) >0 ) obj1 else obj2
}
object FXMain2 extends App{

  println(new ComparableInt(1, 2).greater)

  println(new CommonCompare(Integer.valueOf(10),Integer.valueOf(20)).greater)
  println(new CommonCompare(java.lang.Float.valueOf(10.1f),java.lang.Float.valueOf(11.1f)).greater)

  private val greater: lang.Float = new CommonCompare[lang.Float](11.2f, 12.2f).greater
  println(greater)
}

/**
 * 4. 下界:
 *    在 Java 泛型里表示某个泛型是另外一个泛型的父类型，使用 super 关键字，而在 scala 中, 使用符号“ >: ”, 这种形式称之为泛型的下界。
      T 是 A 的父类:
        Java: <T super A>
        Scala:[T >:A ]
 *    在 Java 中，不支持下界的多界形式。如: <T super A&B> 这是不支持的。
 *    在 Scala 中，对复合类型依然可以使用下界，如：[T >: A with B]。
 */

class Earth{
  def sound()={println("hello")}
}

class Animal extends Earth{
  override def sound(): Unit = println("animal sound")
}

class Bird extends Animal{
  override def sound(): Unit = println("bird sounds")
}

object FXMain3 extends App {
  def biophony[T >: Animal](things:Seq[T]) = things
//  父类
  biophony(Seq(new Earth)).map(_.sound())
//  自己
  biophony(Seq(new Animal)).map(_.sound())
//  子类
  biophony(Seq(new Bird)).map(_.sound())
}

