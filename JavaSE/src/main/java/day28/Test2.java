package day28;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        Account1 account = new Account1();
        IncreaseThread1 increase = new IncreaseThread1(account);
        DecreaseThread1 decrease = new DecreaseThread1(account);
        increase.setName("Increase");
        decrease.setName("Decrease");
        increase.start();
        decrease.start();

//        主线程等待increase和decrease结束
        increase.join();
        decrease.join();
        System.out.println(account);
    }
}

/**
 *   同步代码块第二种方法，在类里加锁
 *   第三种：使用同步方法
 *   第四种：静态同步代码块（对类对象加锁）
 *          先获取Account类的类对象的锁，才能执行静态同步代码块
 *          synchronized (Account.class) {
 *              money++;
 *          }
 */
class Account1{
    private int money = 0 ;

    public Account1() {
    }

//    public void increase(){
//        synchronized (this) {
//            money++;
//        }
//    }
//    public void decrease(){
//        synchronized (this) {
//            money--;
//        }
//    }

    /**
     *  同步方法：
     *    使用synchronized修饰的方法，这个方法就称为同步方法。
     *    执行同步方法，需要先获取调用此方法的对象的同步锁
     *  静态同步方法：
     *    使用synchronized修饰的静态方法，这个方法就被称为静态同步方法
     *    执行静态同步方法，先要获得Account类对象的锁
     */
    public synchronized void increase(){
        money++;
    }
    public synchronized void decrease(){
        money--;
    }
    @Override
    public String toString() {
        return "Account{" +
                "money=" + money +
                '}';
    }
}
class IncreaseThread1 extends Thread {
    private Account1 account;
    public IncreaseThread1(Account1 account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (account) {
                account.increase();
            }
        }
    }
}

class DecreaseThread1 extends Thread {
    private Account1 account;

    public DecreaseThread1(Account1 account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (account) {
                account.decrease();
            }
        }
    }
}