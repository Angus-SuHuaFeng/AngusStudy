package day02

class ScalaList {

}
/**
 *  List
 */
object ScalaList extends App {
  //  创建List
  val list = List(1,2)
  //  访问元素（也可以用index索引访问，例如list(0)）,不过list(0)可以使用list.head代替
  //  结果：List(1, 2)
  println(list)
  //  在尾部追加元素(会生成新的List集合)
  private val list1 :List[Int] = list :+ 90
  //  结果：List(1, 2, 90)
  println(list1)
  //  在首部追加元素
  private val list2: List[Int] = 0 +: list
  //  结果：List(0, 1, 2)
  println(list2)

  //  :: 元素在集合左边 可以将左边添加的元素和原list进行合并，加Nil的作用是原集合不与添加的元素合并
  private val list3: List[Int] = 1 :: 2 :: 3 :: 4 :: list
  private val list4: List[Any] = 1 :: 2 :: 3 :: 4 :: list :: Nil
  //  List(1, 2, 3, 4, 1, 2)
  println(list3)
  //  List(1, 2, 3, 4, List(1, 2))    发现原集合仍然以集合的形式存在
  println(list4)
}
