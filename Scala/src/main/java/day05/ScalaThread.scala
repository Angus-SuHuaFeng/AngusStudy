package day05

class ScalaThread {

}

/**
 * 控制抽象是一类函数:
    1、参数是函数。 例如： f:() => Unit  或  f: => Unit
    2、函数参数没有输入值也没有返回值。  即f: 后为() 或为空 ，=> Unit  推导出来的返回值为Unit类型，即无返回值
 */
object ScalaThread extends App{
  /**
   * f:() 可以简写成f:   即去掉()，这里的f:() 在runThread被调用后可以被定义为任意无返回值的函数进行一系列的操作
   * @param f : 控制抽象
   */
  def runThread (f: () => Unit):Unit ={
    new Thread{
      override def run(): Unit = f()
    }.start()
  }

  /**
   * 这里的() => 推导出来的代码块其实就是上方的f()所代表的代码块，可以理解为：
   * override def run(): Unit = {
        println("111")
        Thread.sleep(2000)
        println("222")
    }
     将() => 推导出的代码块替换到run()方法中，相当于实现了一个线程池，可以在里边写任何想要的操作

   控制抽象的使用方法是：
    方法名 {
      () => 方法体
    }
   */
//  runThread{
//    () => println("111")
//      Thread.sleep(2000)
//      println("222")
//  }

  /**
   * 控制抽象进阶： 结合柯里化实现while循环
   * condition是判断条件,block就是循环体,block中可以写多行代码
   */
  def until(condition: => Boolean)(block: => Unit) {
    if (!condition){
      block
      until(condition)(block)
    }
  }

  var x = 10
  until(x == 100){
    x-=1
    x+=2
    println(x)
  }

}
