package day05

import scala.beans.BeanProperty

/**
 * ！   @BeanProperty : Scala 字段加@BeanProperty时，会自动生成getXXX()和setXXX()的方法。
 *    Person 将会生成四个方法：
   *    --name:String
   *    --name_=(newValue:String): Unit
   *    --getName():String
   *    --setName(newValue:String):Unit
 */
class Person1 {
  @BeanProperty var name :String = _
}

object Person1 extends App{
  private val person = new Person1
  person.getName
  person.setName("Su")
  println(person.getName)
}