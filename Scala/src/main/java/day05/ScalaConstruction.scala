package day05

/**
 * 主构造： class ScalaConstruction(name:String, price:Double)
 *  此处类名后的(name:String,Price:Double) 就是主构造，在创建对象的时候需要设置对应的类属性
 *  主构造器会执行类定义中的所有语句
 *
 *  如果不带 val 和 var 的参数至少被一个方法使用，该参数将自动升级为字段，
 *  这时，name 和 price 就变成了类的不可变字段，而且这两个字段是对象私有的，这类似于 private[this] val 字段的效果
 *  否则（即不带 val 和 var 的参数没有被其他），该参数将不被保存为字段，即实例化该对象时传入的参数值，不会被保留在实例化 后的对象之中。
 *
 *  如果想让主构造器变成私有的，可以在()之前加上 private，这样用户只能通过辅助构造器 来构造对象了
 *  class ScalaConstruction private(name:String, price:Double){}
 * @param name
 * @param price
 */
class ScalaConstruction(name:String, price:Double) {
  def myPrients() = println(name + "," + price)
//  主构造器会执行类定义中的所有语句,在调用主构造器创建对象时，会将类定义中的所有语句一并执行，此处为println(name + "," + price)
  println(name + "," + price)
}

object ScalaConstruction extends App{
//  此处创建对象后发现有输出 MySql实战,21.5 ，说明执行了类定义中的println语句
  private val book1 = new ScalaConstruction("MySql实战", 21.5)
//  book1.myPrients()
}

/**
 * 辅助构造: 辅助构造器名称为 this，通过不同参数进行区分，每一个辅助构造器都必须以主构造器 或者已经定义的辅助构造器的调用开始（ this() ）
 *  结构：
 *      def this(name:String){
          this()
          this.name = name
        }
 *
 */
class Person{
  private var name = ""
  private var age = 0.0
  def this(name:String){
    this()
    this.name = name
  }
  def this(age:Double){
    this()
    this.age = age
  }
  def this(name:String,age:Double){
    this()
    this.name = name
    this.age = age
  }
  def desc = name + " is " + age +" years old"

}
object Person extends App{
  private val person = new Person("Angus",21)
  println(person.desc)
}


