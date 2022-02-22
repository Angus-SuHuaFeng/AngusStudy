package day07

import java.io.PrintStream

trait logger1 {
  def log(msg:String)
}

/**
 * 所有的面向对象的语言都不允许直接的多重继承，因为会出现“deadly diamond of death”问题。
 * Scala 提供了特质（trait），特质可以同时拥有抽象方法和具体方法，一个类可以实现多个特质。
 */

/**
 * 1. abstract class  =  trait 的抽象类用法 ， 当作接口使用
 */
class Logger1 extends logger1{
  //  实现了log方法
  override def log(msg: String): Unit = println(msg)
}

/**
 * 2. 带有具体实现的特质
 */
trait ConsoleLogger1 extends logger1{
//  实现了log方法
  override def log(msg: String): Unit = println(msg)
}

//账户类
class Account1{
  protected var balance = 10.0
}

class draw extends Account with ConsoleLogger1 {
  def withdraw (amount: Double) ={
    if (amount > balance) {
      log("余额不足")
    }else {
      balance -= amount
      log("余额: "+ balance)
    }
  }
}
object draw extends App{
  private val draw1 = new draw
  draw1.withdraw(5)
}

/**
 *  3. 带有特质的对象，动态混入: 在构建对象时混入某个具体的特质，覆盖掉抽象方法
 */

abstract class SavingsAccount extends Account1 with logger1 {
  def withdraw(amount: Double) {
    if (amount > balance)
      log("余额不足")
    else balance -= amount
  }
}

object Test extends App {
  private val account = new SavingAccount with ConsoleLogger1
  account.expense(1)
}
/**
 * 4. 当做富接口使用的特质, 富接口 : 即该特质中既有抽象方法，又有非抽象方法
 */
trait Logger3 {
  def log(msg: String)
  def info(msg: String) {
    log("INFO: " + msg)
  }
  def warn(msg: String) {
    log("WARN: " + msg)
  }
  def severe(msg: String) {
    log("SEVERE: " + msg)
  }
}

trait ConsoleLogger3 extends Logger3{
  override def log(msg: String): Unit = println(msg)
}

class Account3{
  protected var balance = 0.0
}

abstract class SavingAccount3 extends Account3 with Logger3 {
  def withdraw(amount: Double)={
    if (amount > balance){
      severe("余额不足")
    }
    else balance -= amount
  }
}

object Main1{
  def main(args: Array[String]): Unit = {
    val account = new SavingAccount3 with ConsoleLogger3
    account.withdraw(1)
  }
}

/**
 * 5. 特质中的具体字段: 特质中可以定义具体字段，如果初始化了就是具体字段，如果不初始化就是抽象字段。混入该特质的类就具有了该字段，字段不是继承，而是简单的加入类。是自己的字段。
 */
trait Logger4 {
  def log(msg: String)
}
trait ConsoleLogger4 extends Logger4 {
  def log(msg: String) {
    println(msg)
  }
}
trait ShortLogger4 extends Logger4 {
//  定义具体字段
//  val maxLength = 15
//  定义抽象字段
  val maxLength:Int
  abstract override def log(msg: String): Unit = {
    super.log(
      if (msg.length <= 15 ) msg
      else s"${msg.substring(0,msg.length-3)}..."
    )
  }
}
class Account4{
  protected var balance = 0.0
}

class SavingAccount4 extends Account4 with ConsoleLogger4 with Logger4 {
  def withdraw(amount: Double)={
    if (amount > balance){
      log("余额不足")
    }
    else balance -= amount
  }
}
object Main4 {
  def main(args: Array[String]): Unit = {
    val account = new SavingAccount4 with ConsoleLogger4 with ShortLogger4 {
      override val maxLength: Int = 15
    }
    account.withdraw(1)
    println(account.maxLength)
  }
}

/**
 * 6. 特质的构造顺序 ：特质也是有构造器的，构造器中的内容由“字段的初始化”和一些其他语句构成
 */
trait Logger6 {
  println("2. 我在 Logger6 特质构造器中，嘿嘿嘿。。。")
  def log(msg: String)
}

trait ConsoleLogger6 extends Logger6 {
  println("3. 我在 ConsoleLogger6 特质构造器中，嘿嘿嘿。。。")
  def log(msg: String) { println(msg) }
}

trait ShortLogger6 extends Logger6 {
  val maxLength: Int
  println("4. 我在 ShortLogger6 特质构造器中，嘿嘿嘿。。。")
  abstract override def log(msg: String) {
    super.log(if (msg.length <= maxLength) msg
    else s"${msg.substring(0, maxLength - 3)}...")
  }
}
class Account6 {
  println("1. 我在 Account6 构造器中，嘿嘿嘿。。。")
  protected var balance = 0.0
}
abstract class SavingsAccount6 extends Account6 with ConsoleLogger6 with ShortLogger6{
  println("5. 我在 SavingsAccount6 构造器中")
  var interest = 0.0
  override val maxLength: Int = 20
  def withdraw(amount: Double) {
    if (amount > balance) log("余额不足")
    else balance -= amount }
}

/**
 *  构造顺序：
 *  1. 调用当前类的超类（父类）构造器
 *  2. 调用第一个特质的父特质构造器
 *  3. 调用第一个特质的构造器
 *  4. 调用第二个特质的父构造器（已调用过就不再调用了），再调用第二个特质的构造器
 *  5. 调用当前类的构造器
 */
object Main6 extends App {
  val acct = new SavingsAccount6 with ConsoleLogger6 with ShortLogger6
  acct.withdraw(100)
  println(acct.maxLength) }

/**
 *  7. 初始化特质中的字段
 *  特质不能有构造器参数，每个特质都有一个无参数的构造器。缺少构造器参数是特质与类之间唯一的技术差别。
 *  除此之外，特质可以具备类的所有特性，比如具体的和抽象的字段，以及超类。
 *
 */

//现在有如下情景：我们想通过特质来实现日志数据的输出，输出到某一个文件中
trait Logger7 {
  def log(msg:String)
}

trait FileLogger7 extends Logger7 {
  var filename : String
  val out = new PrintStream(filename)

  override def log(msg: String): Unit = {
    out.print(msg)
    out.flush()
  }
}

class SavingsAccount7 {

}

object Main7 {
  def main(args: Array[String]): Unit = {
    val account = new SavingsAccount7 with FileLogger7 {
//      这里会报空指针异常
      /**
       * 异常产生的原因：
       *   创建account对象要调用构造器
       *   1. 调用SavingsAccount7的构造器
       *   2. 调用特质FileLogger7的父构造器
       *   3. 调用特质FileLogger7的构造器
       *    var filename : String
            val out = new PrintStream(filename)
       *   问题来了，调用构造器执行以上代码，创建PrintStream对象时需要filename字段，
       *   而此时该字段还没有赋值，为空，故调用会有空指针异常，想要解决必须要在调用之前初始化filename字段
       */
      override var filename: String = "test.txt"
    }
  }
}

/**
 * 解决控制针异常的方案：
 *  1. 提前定义
 *  2. 使用lazy
 */

/**
 * 1. 提前定义
 *  在new后对需要提前定义的字段进行初始化，初始化后在with要创建的对象的类名，结构为：
 *      new {
 *        override val 字段名 = ???
 *      } with 类名 with 特质
 *  另一种提前定义的方法是在SavingsAccount7中进行提前定义,方法如下

    class SavingsAccount7 extends {
        override var filename: String = _
    }with FileLogger7

 */
object Main8 extends App {
  private val account = new {
    override var filename = "test.txt"
  } with SavingsAccount7 with FileLogger7
}

/**
 * 2. lazy懒执行
 *   对 val out = new PrintStream(filename) 加lazy关键字，当我们使用out对象时在进行创建
 *   即在创建account时，调用构造器后没有执行lazy修饰的语句，将对象创建好后对filename进行赋值，此时对象以及创建好了
 *   但是我们没有使用这个对象，所以lazy修饰的语句仍然没有执行，当使用account对象时，创建out对象发现filename已经被初始化了，因此不会再有空指针异常了

      lazy val out = new PrintStream(filename)
 */



