package day11

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import java.util.UUID

/**
 * 注册worker
 * @param masterHost master的IP地址
 * @param masterPort master的端口
 */
class SparkWorker(masterHost: String, masterPort: Int) extends Actor{
//  初始化连接master配置
  var masterProxy : ActorSelection = _
  val id: String = UUID.randomUUID().toString

//  receive之前先启动: 可以先进行初始化


  override def preStart(): Unit = {
    println("masterProxy初始化...")
    masterProxy =
      context.actorSelection(s"akka.tcp://SparkMaster@${masterHost}:${masterPort}/user/SparkMaster-01")
  }

  override def receive: Receive = {
    case "start" => {
      println("worker启动")
      masterProxy ! RegisterWorkerInfo(id,16,16*1024)
    }


    case RegisterWorkerInfo => {
      println("workerId="+id+"注册成功")
    }
  }
}

object SparkWorker extends App {
  val workerHost = "127.0.0.1"
  val masterHost = "127.0.0.1"
  val workerPort = 10004
  val masterPort = 10005
//  worker启动后注册的地址和端口
  private val config: Config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname=${workerHost}
       |akka.remote.netty.tcp.port=${workerPort}
       |""".stripMargin
  )
  private val sparkWorkerSystem: ActorSystem = ActorSystem("SparkWorker",config)
  private val actorRef: ActorRef = sparkWorkerSystem.actorOf(Props(new SparkWorker(masterHost, masterPort)), "SparkWorker-01")
  actorRef ! "start"
}