package day03

class ScalaSquare {

}

/**
 *
 */
object ScalaSquare extends{
  def unapply (arg:Double):Option[Double] = Some(math.sqrt(arg))

  def main(args: Array[String]): Unit = {
    val num = 36.0
    num match {
      case ScalaSquare(arg) => println(s"Square $num is $arg")
      case _ => println("No match")
    }
  }
}
object Names{
  def unapplySeq(string: String) : Option[Seq[String]] = {
    if (string.contains(",")){
      Some(string.split(","))
    }else{
      None
    }
  }

  def main(args: Array[String]): Unit = {
    val namesString = "Alice,Bob,Thomas"
    namesString match {
      case Names(first, second, third) => {
        println("the three contains three people's names")
        println(s"$first,$second,$third")
      }
      case _ => println("nothing matched")
    }
  }

  val (x, y) = (1, 2)
}


