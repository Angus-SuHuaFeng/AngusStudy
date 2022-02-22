package day05

import scala.collection.mutable.ArrayBuffer

class ScalaInnerClass {

}

class NetWork{
//  内部类
  class Member(val name:String){
    val contacts = new ArrayBuffer[NetWork#Member]()
  }
//  创建成员数组
  val member  = new ArrayBuffer[Member]()

//  加入局域网的方法
  def join(name:String)={
    val m = new Member(name)
    member+=m
    m
  }
}

object NetWork extends App{
  val chat1 = new NetWork
  val chat2 = new NetWork
  private val chat1Number1: chat1.Member = chat1.join("Angus")
  private val chat1Number2: chat1.Member = chat1.join("Nick")
  private val chat2Number1: chat2.Member = chat2.join("Tom")

  chat1Number1.contacts += chat1Number2
  /** 下方代码报错,同一个NetWork的对象只能加入到同一个contacts中
      class Member(val name:String){
          val contacts = new ArrayBuffer[Member]()
      }
   *  发现ArrayBuffer接受的类型为Member, Member又是NetWork的一个内部类, 因此不同的NetWork产生的Member不是相同类
   * type mismatch;
   *  found   : day05.NetWork.chat2.Member
      required: day05.NetWork.chat1.Member

      class Member(val name:String){
        val contacts = new ArrayBuffer[NetWork#Member]()
      }
   *
      NetWork#Member
   */
  chat1Number1.contacts += chat2Number1
  println((for(m <- chat1Number1.contacts) yield m.name).mkString(","))
  private val thisInstance: NetWork.type = NetWork.this
  println(thisInstance)
}

