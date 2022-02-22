package day10

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
 * Actor receive 方法，用于接收
 */

class ActorTest extends Actor {
  override def receive: Receive = {
    case "Hello" => println("Receive Hello")
    case "OK" => println("Receive OK")
    case "exit" => {
      println("exiting...")
      context.stop(self)
//      关闭系统
      context.system.terminate()
    }
    case _ => println("Not Match")
  }
}

object ActorTest extends App {
  private val system: ActorSystem = ActorSystem("ActorTestSystem")
  private val actorRef1: ActorRef = system.actorOf(Props[ActorTest], "ActorRef1")
  actorRef1 ! "OK"
  actorRef1 ! "exit"
}
