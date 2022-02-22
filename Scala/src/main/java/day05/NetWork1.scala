package day05

import scala.collection.mutable.ArrayBuffer

/**
 * 在ScalaInnerClass中我们以及介绍了一种可以接受所有实例的 Member的方法，这里我们还可以用另一种方法：
 * 伴生对象: 将 Member 作为 Network 的伴生对象存在
 */
object NetWork1{
  class Member(val name: String){
    val contacts = new ArrayBuffer[Member]()
//    yield可以将元素打到集合中
    def desc = name + "的联系人" + (for (c <- contacts) yield c.name).mkString(",")
  }

  def main(args: Array[String]): Unit = {
    val chat1 = new NetWork1
    val chat2 = new NetWork1
    val member1 = chat1.join("Angus")
    val member2 = chat1.join("Tom")
    val member3 = chat2.join("Alice")
    member1.contacts += member2
    member1.contacts += member3
    println(member1.desc)
  }
}

class NetWork1{
  private val members = new ArrayBuffer[NetWork1.Member]()

  def join(name:String) ={
    val m = new NetWork1.Member(name)
    members += m
    m
  }
  def desc = "该网络中有: " + (for (m <- members) yield m.desc).mkString(",")
}
