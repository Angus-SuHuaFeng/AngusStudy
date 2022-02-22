package SparkCore.Spark02

import java.io.ObjectInputStream
import java.net.ServerSocket

object Executor {
  def main(args: Array[String]): Unit = {
//    TODO 启动服务器，接收数据
    val serverSocket = new ServerSocket(9999)
    println("服务器启动，等待接收中...")
//    等待客户端连接
    val client = serverSocket.accept()

    val in = client.getInputStream
    val objectIn = new ObjectInputStream(in)
    val task = objectIn.readObject().asInstanceOf[Task]
    val list = task.compute()
    println("接收到客户端发送的数据: " + list)
    objectIn.close()
    in.close()
    client.close()
    serverSocket.close()
  }
}
