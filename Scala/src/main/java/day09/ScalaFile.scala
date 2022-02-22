package day09

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream, PrintWriter}
import scala.io.{BufferedSource, Source, StdIn}

//文件和正则表达式

/**
 * 1. 读取行,读取字符
 */
object ScalaFile extends App {
//  读取文件，返回一个BufferedSource对象
  private val bufferedSource: BufferedSource = Source.fromFile("E:\\IDEADemo\\AngusStudy\\Scala\\src\\main\\java\\day09\\TestFile")
//  通过getLines()得到一个string的迭代器
  private val strings: Iterator[String] = bufferedSource.getLines()
//  还可以转换成Array数组
  private val array: Array[String] = bufferedSource.getLines.toArray
//  文件内容转字符串
  private val string1: String = bufferedSource.mkString
//  mkString加上参数就可以按照某个字符或某个正则表达式进行切分
  private val string2: String = bufferedSource.mkString(",")
//  foreach遍历
  strings.foreach(println)
//  通过for遍历
  for (str <- strings){
    println(str)
  }
//  关闭资源
  bufferedSource.close()
}

/**
 * 2. 读取网络资源、文件写入、控制台操作
 */
object ScalaFile1 extends App{
//  读取网络资源
  private val bufferedSource: BufferedSource = Source.fromURL("https://www.baidu.com")
//  遍历
//  bufferedSource.foreach(print)
//  写入数据到文件
  private val writer = new PrintWriter(new File("E:\\IDEADemo\\AngusStudy\\Scala\\src\\main\\java\\day09\\TestFile"))
//  将网络资源写入文件（多种写入方式）
  writer.write(bufferedSource.mkString)
  bufferedSource.foreach(x => writer.print(x))

  for (i <- 1 to 10){
    writer.println(i)
  }
  writer.close()

//  控制台交互--老 API
  print("请输入内容(旧 API):")
  private val str1: String = Console.readLine()
  println(str1)

  //控制台交互--新 API
  print("请输入内容(新 API):")
  val str2 = StdIn.readLine()
  println(str2)
}

/**
 * 3. 序列化:
 *  对象在传输过程中需要序列化，类如果不序列化会报Exception in thread "main" java.io.NotSerializableException
 *  序列化：
 *    1. extends Serializable
 *    2. @SerialVersionUID(1L)  序列化号
 */
@SerialVersionUID(1L) class Person extends Serializable {
  val name: String = "Nick"
  val age:Int = 21
//  alt + insert 调用
  override def toString = s"Person(name=$name, age=$age)"
}

object Person extends App{
  private val person = new Person
//  写数据
  private val outputStream = new ObjectOutputStream(new FileOutputStream(new File("E:\\IDEADemo\\AngusStudy\\Scala\\src\\main\\java\\day09\\TestFile")))
  outputStream.writeObject(person)
  outputStream.close()
//  读数据
  private val inputStream = new ObjectInputStream(new FileInputStream(new File("E:\\IDEADemo\\AngusStudy\\Scala\\src\\main\\java\\day09\\TestFile")))
  private val value: AnyRef = inputStream.readObject()
  inputStream.close()
  println(value)

}

