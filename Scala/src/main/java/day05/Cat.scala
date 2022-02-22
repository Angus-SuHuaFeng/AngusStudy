package day05

/**
 * 伴生对象：类和它的伴生对象可以相互访问私有特性，他们必须存在同一个源文件中。必须同名
 */
class Cat {
  val hair = Cat.growHair
  private var name = ""
  def changeName (name: String)={
    this.name = name
  }
  def describe ={
    println("hair: " + hair + " name: " + name)
  }
}
object Cat{
  private var hair = 0

  private def growHair ={
    hair += 1
    hair
  }

  def main(args: Array[String]): Unit = {
    val cat1 = new Cat
    val cat2 = new Cat
    cat1.changeName("黑猫")
    cat2.changeName("白猫")
    cat1.describe
    cat2.describe
  }
}