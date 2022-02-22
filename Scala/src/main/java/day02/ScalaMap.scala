package day02

import scala.collection.mutable

class ScalaMap {

}
/**
 *  map
 */
object ScalaMap extends App {
  //  Map为不可变集合,mutable.Map为可变集合
  val map1 = Map("Alice" -> 10 ,"Angus" -> 21)
  val map2 = Map(("Alice",10),("Angus",21))
  val map3 = mutable.Map("Alice" -> 10 ,"Angus" -> 21)
  //  取值
  private val value: Int = map1("Angus")
  //  private val value: Int = map1.get("Angus").get
  println(value)

  //  修改值(只有可变集合可以修改)
  map3("Angus") = 20
  println(map3)
  //  增加键值对
  map3 += ("ZQQ" -> 20)
  println(map3)
  //  移除键值对
  map3 -= ("Alice")
  println(map3)
  //  集合1 + 集合2
  private val map4: mutable.Map[String, Int] = map3 + ("Alice" -> 10)
  println(map4)
  //  遍历
  /** 1. 第一种遍历方法返回的是kv元组
   * (Angus,20)
   * (Alice,10)
   * (ZQQ,20)
   */
  for (elem <- map4) {
    println(elem)
  }

  /**
   *  2. 第二种可以得到key和value
   *  key: Angus value: 20
   *  key: ZQQ value: 20
   */
  for ((k,v) <- map3){
    println("key: " + k+ " value: " + v)
  }

  /**
   *  3. 第三种可以分别得到key和value
   */
  for (k <- map3.keys){
    println("key: " + k)
  }
  for (v <- map3.values){
    println("value: " + v)
  }
}
