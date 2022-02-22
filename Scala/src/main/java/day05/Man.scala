package day05

/**
 * apply()的用法：
 *  1. apply 方法一般都声明在伴生类对象中，可以用来实例化伴生类的对象
 *  2. 也可以用来实现单例模式
 */
class Man (val gender:String,name:String){
  def desc ={
    println("Gender: "+ gender + "\tName: " + name)
  }

  /**
   * 实现单例模式
   */

}

object Man{
//  apply()可以用来实例化伴生类的对象
  def apply(gender: String,name :String): Man = new Man(gender, name)

  def main(args: Array[String]): Unit = {
//    在object中直接使用Man()会调用apply()方法创建对象，所以可以不写new
    val man1 = Man("男", "Angus")
    val won2 = Man("女","ZQQ")
    man1.desc
    won2.desc
  }

  /**
   * 懒汉式：没有的话才创建
   */
  var instance : Man = null
  def apply(name: String)={
    if (instance==null){
      instance = new Man("男",name)
    }
    instance
  }

}
