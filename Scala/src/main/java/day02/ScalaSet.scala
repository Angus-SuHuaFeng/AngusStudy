package day02


import scala.collection.mutable


class ScalaSet {

}
/**
 *  Set: set中只保存非重复元素, 默认以哈希值排序
 *  Scala默认使用不可变的Set, mutable.set为可变
 *  排序可以使用SortedSet进行排序, 排序算法使用红黑树实现
 *
 */
object ScalaSet extends App{
//  只有可变集合可以添加元素
  private val set1 = Set(1, 2, 3)
  private val set2: mutable.Set[Int] = mutable.Set(1, 2, 3)
//  添加元素
  set2.add(4)
  set2 += 5
//  Set(1, 5, 2, 3, 4)
  println(set2)

//  删除元素
//  remove(5) 是以元素值删除,不是索引
//  Set(1, 2, 3, 4)
  set2.remove(5)
  println(set2)
//  以元素值删除,不是索引
  set2 -= 4
  println(set2)

//  俩个集合相加  ++
//  原set2集合 Set(1, 2, 3)
  private val set3: mutable.Set[Int] = set2 ++ mutable.Set(4,5,6)
//  相加后的集合Set(1, 5, 2, 6, 3, 4)
  println("相加后的: "+set3)
//  交集  &
  private val set4: mutable.Set[Int] = set2 & set3
  println("交集: "+set4)
//  差集  &~
  private val set5: mutable.Set[Int] = set3 &~ set2
  println("差集: "+set5)
}