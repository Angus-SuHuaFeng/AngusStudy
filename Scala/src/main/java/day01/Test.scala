package day01

import scala.util.control.Breaks

object Test1 {
  def main(args: Array[String]): Unit = {

    /**
     * 1. if else 有返回值
     */
    val a1 = 10
    val a2:String = {

      if(a1 > 20){
        "a1大于20"
      }else{
        "a1小于20"
      }

    }
    println(a2)
  }
}
object Test2{
  def main(args: Array[String]): Unit = {
    /**
     * 2. while循环的打断方法Breaks
     */
    val loop = new Breaks()
    var n = 1
    loop.breakable(while (n <= 10){
//      scala中没有++或--操作 ，但是有+=
      n += 1
      if (n==5){
        loop.break()
      }
    })
    println(n)
  }
}

object Test3{
  /**
   *  key -> value  : Map的结构
   *  变量 <- 数据值
   *  a to b  即[a,b]  to 是闭区间，例如  1 to 10 则为 1，2，3，4，5，6，7，8，9，10
   *  a until b 即[a,b) 左闭右开
   *
   */
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 3 ; j <- 1 to 3 ; k <- 1 to 3){
      println(i , j , k)
    }
    for (i <- 1 to 10 ; if (i %2 == 0)){
      println(i)
    }
  }
}

object Test4 extends App {
  /**
   *  def 函数名(参数1 : 参数类型, 参数2 : 参数类型, ......) : 返回值类型 = { 函数体 }
   *
   *  其中 [: 返回值类型 =] 可省略
   *
   *  a:Int* 表示可变类型（scala中）
   *  String a... 表示可变类型（java中）
   *
   *  参数可以有默认值,在类型后加 =value 例如x:Int=1 ,  如果对有默认值的参数进行赋值，则会覆盖默认值
   */
//  def sum(x:Int=1,y:Int=1): Int ={
//    x+y
//  }
//  println(sum(1,2))
//  println(sum())
}

object Test5 extends App {
//  Int*的用法，可以将多个参数合并成一个args（也可以命名为其他）
  def sum(args:Int*): Int ={
    var result = 0
//    迭代args
    for (arg <- args){
      result+=arg
    }
    result
  }
  println(sum(1,2,3))
}

object Test6 extends App {

  def func(n:Int): Int ={
    if (n==1){
      1
    }else{
      n * func(n-1)
    }
  }
  println(func(5))
}

object Test7 extends {
  /**
   *  Lazy
   */

  def init()={
    println("init已执行")
    "hahaha"
  }

  def main(args: Array[String]): Unit = {
    lazy val msg = init()
    println("lazy没有执行")
    println(msg)
  }
}

object ScalaException extends App {
  def divide(x:Float,y:Float)={
    if (y==0){
      throw new Exception("0不能为除数")
    }else{
      x/y
    }
  }

  try {
    println(divide(1, 0))
  } catch {
    case exception: Exception => println("捕获异常：" + exception)
  }
}
