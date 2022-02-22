package day02

import scala.collection.mutable.ArrayBuffer

class ScalaArray{

}
object ScalaArray{
  /**
   *  定长数组
   */
  object ScalaImmutableArray extends App {
    //  定义数组
    val arr1 = new Array[Int](10)
    arr1(1) = 1
    //  foreach遍历
    arr1.foreach(arr1 => println(arr1))
    arr1.foreach(println)
    //  java方式的foreach遍历（适用于修改值）
    for (elem <- arr1){
      println(elem)
    }
    //  或者直接定义
    val arr2 = Array(1,2,3,4,5)
    arr2.foreach(arr2 => println(arr2))
    arr2.foreach(println)
  }

  /**
   *  变长数组
   */
  object ScalaMutableArray extends App {
    //  定义变长数组
    var arr1 = ArrayBuffer[Int]()
    arr1.append(1)
    arr1.append(2)
    arr1.foreach(arr1 => println(arr1))

    //  变长转定长
    val arr2 = arr1.toArray
    //  定长转变长
    val arr3 = arr2.toBuffer
  }

  /**
   *  多维数组
   */
  object ScalaOfDimArray extends App {
    var arr1 = Array.ofDim[Int](3,4)
    arr1(1)(1) = 1
    println(arr1)
  }
}
