package day05

//class Dog {
//  private var leg = 4
//  def shout(content : String) : Unit={
//    println(content)
//  }
////  相当于Java的get方法
//  def currentLeg : Int = leg
//}

/**
 * 自己手动创建变量的 getter 和 setter 方法需要遵循以下原则：
 * 1) 字段属性名以“_”作为前缀，如：_leg
 * 2) getter 方法定义为：def leg = _leg
 * 3) setter 方法定义时，方法名为属性名去掉前缀，并加上后缀，后缀是：“leg_=”,leg_=必须连着写，中间不能有空格
 */
class Dog{
  private[this] var _leg = 4
//  get
  def leg = _leg
//  set
  def leg_= (newLeg : Int){
    _leg = newLeg
  }

  def shout(content : String) : Unit={
    println(content)
  }
}


object Dog extends App{
  val dog = new Dog
  dog shout("汪汪汪")
//  getter
  println(dog.leg)
//  setter   只有变量由var定义才可以修改值
  dog.leg_= (10)
  println(dog.leg)
}

/**
 * 变量：workDetails 在封闭包 professional 中的任何类中可访问。
 * 封闭包：friends 的任何类都可以被 society 包中任何类访问。
 * 变量：secrets 只能在实例方法的隐式对象(this)中访问
 */
package society {
  package professional {
    class Executive {
//      private[professional] 表示可以在professional包中的任何类访问到
      private[professional] var workDetails = null
//      可以被 society 包中任何类访问。
      private[society] var friends = null
//      只能在实例方法的隐式对象(this)中访问
      private[this] var secrets = null
      def help(another: Executive) {
        println(another.workDetails)
        println(this.secrets)      //报错：访问不到
        }
      }
    }
}