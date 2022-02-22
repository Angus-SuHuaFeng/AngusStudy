package day11

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.mutable

class SparkMaster extends Actor{
//  创建存放worker的容器
  val workers: mutable.Map[String, WorkerInfo] = mutable.Map[String, WorkerInfo]()

  override def receive: Receive = {
    case "start" => println("master服务开启")
    case RegisterWorkerInfo(id,cpu,ram) => {

      if (!workers.contains(id)){
//        如果worker容器中不存在该worker就创建该id的worker对象
        val workerInfo = new WorkerInfo(id, cpu, ram)
//        将新创建的workerInfo对象放入workers中
        workers += ((id,workerInfo))
        println("workers = " + workers)

//        回复消息，注册成功
        sender() ! RegisterWorkerInfo
      }
    }
  }

}

object SparkMaster extends App {
//  通信方式，通信地址，通信端口 => config
  private val config: Config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname=127.0.0.1
       |akka.remote.netty.tcp.port=10005
       |""".stripMargin
  )
//  创建Actor
  private val sparkMasterSystem: ActorSystem = ActorSystem("SparkMaster", config)
//  选择ActorRef
  private val sparkMasterRef: ActorRef = sparkMasterSystem.actorOf(Props[SparkMaster], "SparkMaster-01")
//  启动
  sparkMasterRef ! "start"
}
















