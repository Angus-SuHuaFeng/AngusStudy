package day07

import java.util.Date

trait ScalaTrait {

}

/**
 * 1. 最顶层的特质： 无方法体
 */
trait Logger{
  def log(msg:String)
}

/**
 * 2. 继承1，重写方法体
 */
trait ConsoleLogger extends Logger{
  override def log(msg: String): Unit = println(msg)
}

/**
 * 3. 继承2, 重写方法体 + 时间
 */
trait TimestampLogger extends ConsoleLogger{
  override def log(msg: String): Unit = super.log(msg + new Date)
}

/**
 * 4. 继承2, 重写方法体 + 打印长度 ShortLogger
 */
trait ShortLogger extends ConsoleLogger{
  override def log(msg:String) ={
    super.log(
      if ( msg.length <= 12){
        msg
      }else{
        s"${msg.substring(0,12)}..."
      }
    )
  }
}

/**
 * 5. 银行账户(余额)
 */
class Account{
  protected var balance = 0.0
}

/**
 * 存钱(deposit) or 消费(expense)
 */
abstract class SavingAccount extends Account with Logger{
  def deposit(amount: Double): Unit ={
    balance+=amount
    log("存入"+amount)
  }
  def expense(amount: Double): Unit ={
    if (amount > balance){
      log("余额不足1234567890")
    }else{
      balance-=amount
      log("余额"+balance)
    }
  }
}

object Main{
  def main(args: Array[String]): Unit = {
//    说明在调用特质的方法时为从右到左
    val account1 = new SavingAccount with ShortLogger with TimestampLogger
    account1.deposit(100)
    account1.expense(101)
    println("---------------------------")
    val account2 = new SavingAccount with TimestampLogger with ShortLogger
    account2.deposit(100)
    account2.expense(101)
  }
}