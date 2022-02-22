package SparkCore.Spark02

import java.io.ObjectOutputStream
import java.net.Socket

object Driver {
  def main(args: Array[String]): Unit = {

//    连接服务器
    val client = new Socket("localhost",9999)

    val outputStream = client.getOutputStream
    val objectOutputStream = new ObjectOutputStream(outputStream)
    val task = new Task()
    objectOutputStream.writeObject(task)
    outputStream.flush()
    objectOutputStream.close()
    println("客户端发送数据完成")
    outputStream.close()
    client.close()
  }
}
