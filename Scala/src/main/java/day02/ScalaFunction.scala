package day02

class ScalaFunction {

}


object ScalaFunction extends App {
  /**
   * 1. map()函数      将集合中的元素进行相应处理   结果List(alice, angus, bob)
   * 2. flatMap()函数  flatMap会将字符串中的所有字符进行处理   结果: List(a, l, i, c, e, a, n, g, u, s, b, o, b)
   */
  val names = List("Alice","Angus","Bob")
//  结果List(alice, angus, bob)
  println(names.map(_.toLowerCase()))   // _ 代表全部元素
  println(names.map(x => x.toLowerCase()))
//  结果: List(a, l, i, c, e, a, n, g, u, s, b, o, b), flatMap会将字符串中的所有字符进行处理
  println(names.flatMap(_.toLowerCase()))

  /**
   * 3. reduceLeft()函数
   * 4. reduceRight()函数
   */
  val value = List(1,2,3,4)
  private val i1: Int = value.reduceLeft(_ + _)
  //  reduceRight表示从左开始, 1-2-3-4
  private val i2: Int = value.reduceLeft(_ - _)
  //  reduceRight表示从右开始, 4-3-2-1
  private val i3: Int = value.reduceRight(_ - _)
  println(i1)
  println(i2)
  println(i3)

  /**
   * 5. fold()函数{等价于foldLeft()函数}: 折叠操作
   * 6. foldRight()函数  foldRight() == :/
   * 7. foldLeft()函数  foldLeft == fold == /:
   */
  private val list = List(1, 9, 2, 8)
//  计算过程是这样的  1-(9-(2-(8-100)))   先8-100 再2-(-92) 再 9-94 再 1-(-85) 最终得86
  private val i: Int = list.foldRight(100)(_ - _)
//  100-1-9-2-8   foldLeft等价于fold, foldLeft == fold == /:
  private val i4: Int = list.foldLeft(100)(_ - _)
  private val i5: Int = (100 /: list) (_ - _)
  println(i4)
  println(i5)

  val sev = "我的爱好是: 学习,学习,学习!!!"
  val i6 = (Map[Char, Int]() /: sev) ((k,v) => k + (v -> (k.getOrElse(v,0) + 1)))
  println(i6)
//  打印过程 Vector(0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55)
  println((1 to 10).scanLeft(0)(_ + _))

  /**
   * 7. zip() 拉链函数
   */
  private val list1 = List("A", "B", "C")
  private val list2 = List(1, 2, 3)
  private val list3: List[(String, Int)] = list1.zip(list2)
  println(list3)
  for (elem <- list3) {
    println(elem)
  }

  /**
   * 8. Iterator迭代器
   */
  private val iterator: Iterator[Int] = List(1, 2, 3, 4).iterator
//  遍历
//  方法一
  while (iterator.hasNext){
    println(iterator.next())
  }
//  方法二
  iterator.foreach(println)
//  方法三
  iterator.foreach(x => println(x))

}


