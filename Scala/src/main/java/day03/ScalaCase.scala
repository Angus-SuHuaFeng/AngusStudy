package day03

class ScalaCase {

}

/**
 * 类型模式：可以匹配对象的任意类型，但是不能直接匹配泛型类型。这样做的意义在于，避免了使用 isInstanceOf 和 asInstanceOf 方法
 */
object ScalaCase extends App {
  /**
   * 1. 当a=4时,obj为 Map("aa" -> 1)，最终的r1应该是  "Map[String, Int]类型的 Map 集合"
   * 2. 当a=5时,obj为 Map(1 -> "aa")，最终的r1应该是  "Map 集合" ，但是实际上仍然为 "Map[String, Int]类型的 Map 集合"
   * 3. 综上，Map 类型的泛型在匹配的时候，会自动删除泛型类型，只会匹配到 Map 类型，例如Map[String,Int] 最终只能匹配为Map[]
   * 4. 在a= 4 或 5 时，都会被第一个 Map 拦截，它认为 就是一个 Map，其中的[String,Int]或[Int，String]都被擦除，Array 的不会擦除
   *              case m: Map[String, Int] => "Map[String, Int]类型的 Map 集合"
   */
  val a = 8
  val obj =
    if(a == 1) 1
    else if(a == 2) "2"
    else if(a == 3) BigInt(3)
    else if(a == 4) Map("aa" -> 1)
    else if(a == 5) Map(1 -> "aa")
    else if(a == 6) Array(1, 2, 3)
    else if(a == 7) Array("aa", 1)
    else if(a == 8) Array("aa")
  val r1 = obj match {
    case x: Int => x
    case s: String => s.toInt
    case BigInt => -1 //不能这么匹配
    case _: BigInt => Int.MaxValue
    case m: Map[String, Int] => "Map[String, Int]类型的 Map 集合"
    case m: Map[_, _] => "Map 集合"
    case a: Array[Int] => "It's an Array[Int]"
    case a: Array[String] => "It's an Array[String]"
    case a: Array[_] => "It's an array of something other than Int"
    case _ => 0 }
  println(r1 + ", " + r1.getClass.getName)

  /**
   * 匹配数组
   *
   */
  for (arg <- Array(Array(0),Array(1,0),Array(0,1,0),Array(1,1,0),Array(1,1,0,1),Array("a","b","c","d"))){
    val result = arg match {
      case Array(0) => "0"
      case Array(0,_*) => "0开头"
      case Array(x,y) => "x,y: "+ x + " "+ y
      case Array(x,y,z) => "x,y,z: " + x + " "+ y + " " + z
      case s : Array[String] => "String"
      case _ => "其他类型"
    }
    println(result)
  }

  /**
   * 匹配列表(List)
   */
  private val str: String = List(1, 0) match {
    case x :: y :: Nil => x + " " + y
  }
  println(str)

  /**
   * 匹配元组
   */
  for (pair <- Array((1,0),(0,1),(1,1))){
    val str1 = pair match {
      case (0, _) => "0..."
      case (x, 0) => x + " 0"
      case _ => "neither is 0"
    }
    println(str1)
  }
}


