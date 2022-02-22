package day03

abstract class Item
case class Article( description: String, price: Double) extends Item
case class Bundle(description: String, discount: Double, item: Item*) extends Item

/**
 * 绑定： 别名@被绑定变量
 *  例如： Bundle(_,_,art@Article(name@_, price@_),ret@_*)
 *        art@Article(name@_, price@_)     绑定后可以通过art访问Article(name@_, price@_)
 *        name@_, price@_                  绑定后可以通过name和price分别访问第一个_和第二个_所代表的值
 *
 */
object Test extends App{
  val sale = Bundle("愚人节大甩卖系列", 10,
                Article("《九阴真经》", 40),
             Bundle("修炼之路系列", 20,
                Article("《捡起》", 80),
                Article("《名字》",30)))

//  单个书名匹配
  var result1  = sale match {
    case Bundle(_,_,Article(desc, _),_*) => desc
  }
//  《九阴真经》
  println(result1)
  /**
   * 用ret绑定_*指代的其他元素：
   *  ret结果为： WrappedArray(Bundle(修炼之路系列,20.0,WrappedArray(Article(《捡起》,80.0), Article(《名字》,30.0))))
   *  为WrappedArray类型，绑定数组
   */
  var result2  = sale match {
    case Bundle(_,_,art@ Article(name@ _, price@ _),ret@ _*) => (art, ret)
  }
  println(result2)
  /**
   * 用ret直接代替其他元素：
   *  结果为： Bundle(修炼之路系列,20.0,WrappedArray(Article(《捡起》,80.0), Article(《名字》,30.0)))
   *  没有WrappedArray类型，仅仅是原有的类型
   *
   *  所以绑定类型的元素如果有多个，则会放到一个绑定数组中，即WrappedArray类型的数组中
   */
  var result3  = sale match {
    case Bundle(_,_,art@ Article(name@ _, price@ _),ret) => (art, ret)
  }
  println(result3)

  /**
   * 计算价格的函数
   * @param it
   * @return
   */
  def price(it: Item):Double ={
    it match {
      case Article(_,price) => price
      case Bundle(_,disc,its @ _*) => its.map(price).sum - disc
    }
  }
  println(price(sale))

  /**
   * 偏函数：
   *    偏函数只对会作用于指定类型的参数或指定范围值的参数实施计算
   */
    val f1 = new PartialFunction[Any,Int] {
//      如果为Int类型，返回True，反之则返回False
      override def isDefinedAt(x: Any): Boolean = {
        if (x.isInstanceOf[Int]){
          true
        }else{
          false
        }
      }

      override def apply(v1: Any): Int = v1.asInstanceOf[Int] + 1
    }
  var rf1 = List(1,3,4,"six") collect f1
  println(rf1)
  println(f1.isDefinedAt("six"))
  println(f1.isDefinedAt(1))

  /**
   * f1优化写法
   */
  val f2:PartialFunction[Any,Int] = {
    case i : Int => i +1
  }

  var rf2 = List(1,3,4,"six") collect(f2)
  println(rf2)
  println(f2.isDefinedAt("six"))
  println(f2.isDefinedAt(1))

}










