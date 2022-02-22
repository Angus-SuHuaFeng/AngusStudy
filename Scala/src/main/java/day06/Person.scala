package day06

/**
 * 继承：和 Java 一样使用 extends 关键字，在定义中给出子类需要而超类没有的字段和方法，或者重写超类的方法
 *
 * 注意： 如果类声明为 final，他将不能被继承。如果单个方法声明为 final，将不能被重写。
 */

class Person (val name: String,val age:Int){
  var gender = ""
  override def toString: String = getClass.getName +"{" + name + "}"
}

class Employee(val salary: Double, name:String, age: Int) extends Person(name,age){
  def description = name + " " + salary

}

/**
 * 重写方法: 重写一个非抽象方法需要用 override 修饰符，调用超类的方法使用 super 关键字
 *
 * 超类的构造: 只有主构造器可以调用超类的构造器, 辅助构造器永远都不可能直接调用超类的构造器
 *              class Student(name:String,age:Int) extends Person(name, age)
 *
 */

class Student(name:String,age:Int) extends Person(name, age){
  var sno = 0.0
  def this(sno:Int, name: String,age: Int){
//    每个辅助构造器都必须以对先前定义的辅助构造器或主构造器的调用开始, 这里Student的主构造器有(name:String,age:Int), 故需要调用这个构造器 this(name,age)
    this(name,age)
    this.sno = sno
  }
  override def toString: String = super.toString + sno
}

/**
 * 类型检查和转换:
 *  .isInstanceOf(T) : 同 Java 的 obj instanceof T  判断某个对象是否为T类型，例如 Scala: str.isInstanceOf[String]  Java: str isInstanceOf[String]
 *  .asInstanceOf[T] : 同 Java 的 (T)obj, 就是强制转换
 *  classOf[T] : classOf 获取对象的类名
 */

object Test extends App {
//  class java.lang.String
  println(classOf[String])
  var price = 10.1
//  true
  println(price.isInstanceOf[Double])
//  10
  println(price.asInstanceOf[Int])
}

/**
 * protected  : protected 在 scala 中比 Java 要更严格一点，即， 只有继承关系才可以访问，同一个包下， 也是不可以的
 */

/**
 * 重写字段：
 * 1、def 只能重写另一个 def
 * 2、val 只能重写另一个 val 或不带参数的 def
 * 3、var 只能重写另一个抽象的 var
 */
class Person1(val name:String,var age:Int){
  println("主构造器已经被调用")
  val school="五道口职业技术学院"
  def sleep="8 hours"
  override def toString="我的学校是：" + school + " 我的名字和年龄是：" + name + "," + age
}

class Person2(name:String, age:Int) extends Person1(name, age){
  override val school: String = "清华大学"
}

object Person2 extends App{
  private val angus = new Person2("Angus", 21)
  override def toString = super.toString
  println(angus.toString)
}
//抽象 var
abstract class Person3 {
  var name:String
}

class Person4 extends Person3{
  override var name: String = _
}

/**
 * 匿名子类：通过包含带有定义或重写的代码块的方式创建一个匿名的子类
 */
object Test1 extends App{
  val person = new Person("Angus", 21) {
    def des = name + age
    gender = "男"
  }
  println(person.gender)
  println(person.des)
}

/**
 * 抽象类：可以通过 abstract 关键字标记, 抽象类不能被实例化。
 * 方法不用标记 abstract，只要省掉方法体即可。类可以拥有抽象字段，抽象字段就是没有初始值的字段。
 * 继承后需要实现抽象类中的方法和参数，可以不加override关键字
 */

abstract class Person5{
  val id : Int
  var name : String
//  方法不用标记 abstract, 无方法体
  def idString : Int
}

class Worker extends Person5{
  val id: Int = 1
  var name: String = _
  def idString: Int = ???
}

/**
 * 构造顺序和提前定义: 当子类重写了父类的方法或者字段后，父类又依赖这些字段或者方法初始化，这个时候就会出现问题
 */
class Creature {
  val range: Int = 10
  val env: Array[Int] = new Array[Int](range)
}

class Ant extends Creature {
//  如果将下行代码注释，最终env的长度为10
  override val range = 2
}

object Ant extends App{
  private val ant = new Ant1
//  0
  println(ant.env.length)
}

/**
 * 解决方法：
 * 1) 可以将 val 声明为 final，这样子类不可改写。
 * 2) 可以将超类中将 val 声明为 lazy，这样安全但并不高效。
 * 3) 还可以使用提前定义语法，可以在超类的构造器执行之前初始化子类的 val 字段
 */
//提前定义语法
class Ant1 extends {
  override val range = 2
} with Creature


