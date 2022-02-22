package day05

class ScalaEnum {

}

/**
 * 枚举： 继承Enumeration，Value方法创建枚举类型
 */
object ScalaEnum extends Enumeration with App {
  val RED = Value(0,"STOP")
  val GREEN = Value(1,"GO")
  val YELLOW = Value(2,"SLOW")

  println(ScalaEnum.RED.id)

}

