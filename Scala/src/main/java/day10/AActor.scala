package day10

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
 *
 * @param actorRef 另一个actor的代理（引用）
 */
class AActor(actorRef: ActorRef) extends Actor{
//  接收BActor发送的消息
  var bActorRef: ActorRef = actorRef

  override def receive: Receive = {
    case "Hello" => {
      println("A收到: Hello")
      bActorRef ! "Hi"
    }
    case "你吃饭了吗" => {
      println("A收到: 你吃饭了吗")
      Thread.sleep(1000)
      bActorRef ! "吃了，你呢"
    }
    case "exit" => {
      bActorRef ! "Bye"
      context.stop(self)
      //      关闭系统
      context.system.terminate()
    }
  }
}

class BActor(actorRef: ActorRef) extends Actor{
//  接收AActor的消息
  var aActorRef: ActorRef = actorRef
  override def receive: Receive = {
    case "Hi" => {
      println("B收到: Hi")
      aActorRef ! "你吃饭了吗"
    }
    case "吃了，你呢" => {
      println("B收到: 吃了，你呢")
      Thread.sleep(1000)
      aActorRef ! "exit"
    }
    case "exit" => {
      aActorRef ! "Bye"
      context.stop(self)
      //      关闭系统
      context.system.terminate()
    }

  }
}

object ActorTalk extends App{
  private val factorActor: ActorSystem = ActorSystem("FactorActor")
  private val aActor: ActorRef = factorActor.actorOf(Props(new AActor(bActor)), "AActor")
  lazy private val bActor: ActorRef = factorActor.actorOf(Props(new BActor(aActor)), "BActor")
  aActor ! "Hello"
}


