package SparkCore.Spark02

class Task extends Serializable {
  val data = List(1,2,3,4)

  val login =
    (num: Int) => num * 2

  def compute()={
    data.map(login)
  }

}
