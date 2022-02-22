package day02

import scala.collection.mutable

class ScalaQueue {

}
/**
 *  队列: 先进先出FIFO
 *  添加元素从尾部添加,移除元素从头部移除
 */
object ScalaQueue extends App {
  private val queue = new mutable.Queue[Int]()
  println(queue)

  //  添加元素
  queue ++= List(1,2,3)
  println("添加元素后:" + queue)

  //  移除元素(返回值为被删除的元素)
  private val i: Int = queue.dequeue()
  println("移除元素后:" + queue)
  //  enqueue添加元素,只能在尾部添加元素
  queue.enqueue(3,4)
  println(queue)
  /**
   * head: 第一个元素
   * tail: 除去第一个元素的所有元素
   * last: 最后一个元素
   */
  private val head: Int = queue.head
  private val tail: mutable.Queue[Int] = queue.tail
  private val last: Int = queue.last
  //  此时的queue为 Queue(2, 3, 3, 4)
  //  结果为2
  println(head)
  //  结果为 Queue(3, 3, 4)    可以看出是除去第一个元素的所有元素的队列
  println(tail)
  //  结果为4
  println(last)
}
