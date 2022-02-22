package day02

class ScalaTuple {

}
/**
 *  元组tuple
 *    * 最多只能有22个元素
 *    * 元组的索引从1开始
 */
object ScalaTuple extends App{
  val tuple1 = (1,2,3,"haha")
  println(tuple1._4)

  //  元组遍历
  tuple1.productIterator.foreach(println)

  tuple1.productIterator.foreach(i => println(i))

  for (elem <- tuple1.productIterator){
    println(elem)
  }
}