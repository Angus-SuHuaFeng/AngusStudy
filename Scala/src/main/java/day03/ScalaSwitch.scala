package day03

class ScalaSwitch {

}

/**
 * Scala中的Switch为match(匹配)
 */
object ScalaSwitch extends App{
  var result = 0
  val op: Char = '+'

  op match {
    case '+' => result = 1
    case '-' => result = -1
    case _ => result = 3
  }
  println(result)
//  +	-	3	!
  for (ch <- "+-3!"){
    print(ch+"\t")
  }
  println()
  for (ch <- "+-3!"){
    var sign = 0
    var dign = 0
    ch match {
      case '+' => sign = 1
      case '-' => sign = -1
      case _ if ch.toString.equals("3") => dign = 3
      case _ => sign = 0
    }
    println(ch + "\tsign: " + sign + "\tdign: " + dign)
  }
}



