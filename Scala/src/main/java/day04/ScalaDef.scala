package day04

class ScalaDef {

}

object ScalaDef extends App{
  def plus(x:Int) = 3 + x
  println(plus(1))

  /**
   * 匿名函数: 没有函数名的函数，可以通过函数表达式来设置匿名函数，（用变量来承接一个函数）
   */
  val tripe = (x:Int) => x + 3
  println(tripe(1))

  /**
   * 高阶函数：能够接受函数作为参数的函数
   */
  def highOrderFunction (f : Double => Double, i: Int) = f(i)
  def minus (x: Double) = x - 7
  var result  = highOrderFunction(minus,10)
  println(result)

  /**
   * 闭包：一个函数把外部不属于自己的参数也包含进来
   * 1) 匿名函数(y: Int) => x -y 嵌套在 minusxy 函数中。
   * 2) 匿名函数(y: Int) => x -y 使用了该匿名函数之外的变量 x
   * 3) 函数 minusxy 返回了引用了局部变量的匿名函数
   */

//  这就是一个闭包，y是不属于minusxy函数的参数2
  def minusxy (x: Int) = (y:Int) => x - y
//  函数 minusxy 返回了引用了局部变量的匿名函数f1，也是闭包的一种
  val f1 = minusxy(10)
  println(f1(3))



}
