package day02

class ScalaStream {

}

/**
 * Stream流:
 *  tail: tail的每一次使用都会向Stream集合中按照规则动态生成新的元素
 */
object ScalaStream extends App {
  def numf(n:BigInt) : Stream[BigInt]= n #:: numf(n+1)
//  结果为Stream(1, ?)
  println(numf(1))
  private val stream: Stream[BigInt] = numf(10)
  println(stream.tail)
  println(stream)
  println(stream.tail.tail)
  println(stream)
//  使用map映射Stream的元素进行一些计算
  println(stream.map(x => x*x))
}
