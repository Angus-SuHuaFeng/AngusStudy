package day03

/**
 * 样例类： 被case修饰的类
 */
abstract class Amount
case class Dollar(value: Double) extends Amount
case class Currency(value: Double, unit: String) extends Amount
case object Nothing extends Amount


object Amount extends App{
  for (amt <- Array(Dollar(1000.0),Currency(1000.0,"EUR"),Nothing)){
    val result = amt match {
      case Dollar(v) => "$" + v
      case Currency(_, v) => "EUR " + _
      case Nothing => ""
    }
    println(amt + ": " + result)
  }
}

/**
 * Copy(): Copy操作生成的是新变量，而不是修改原数据
 */
object ScalaCopy extends App{
  val amt = Currency(29.95,"EUR")
  val price = amt.copy(value = 19.95)
  println(amt)
  println(price)
  println(amt.copy(unit = "CHF",value = 1))
}

object ScalaMid extends App{
  List(1, 7, 2, 9) match {
    case first :: second :: rest => println(first + second + rest.length)
    case _ => 0
  }
}





