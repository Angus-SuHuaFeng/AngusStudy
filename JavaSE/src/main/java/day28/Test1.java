package day28;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();
        IncreaseThread increase = new IncreaseThread(account);
        DecreaseThread decrease = new DecreaseThread(account);
        increase.setName("Increase");
        decrease.setName("Decrease");
        increase.start();
        decrease.start();

//        主线程等待increase和decrease结束
        increase.join();
        decrease.join();
/**
 *  Account{money=276}
 *  发现结果每次都不一样，并不是0
 *  如果为单线程，则结果一定是0
 *  多线程并发的情况下结果就未知了，因为每次线程都不一定可以执行完毕，可能中途失去执行权，
 *  比如100+1计算完得到101，但是并没有来得及存入堆中线程就已经丧失执行权了，则会在其他线程结束后继续执行
 *
 *  因此，在多线程中我们要保证操作是原子的（不可再分，必须全部执行完才可以），这就用到了同步代码块
 */
        System.out.println(account);

    }
}

/**                     要做线程同步，必须保证多个线程存在竞争关系（用锁）
 *    同步代码块（第一种，最常用）:
 *       1. 每个对象都有且只有一个锁，这个锁叫同步锁
 *       2. 同步锁在同一时刻只能被一个线程获取
 *       执行同步代码快必须先获得account对象的同步锁
 *       没有同步锁的线程必须等待别的线程释放锁
 *       如果线程执行完同步代码块就会释放锁
 *       如果一个线程需要的到锁，但是没有得到锁，那么它会处于阻塞状态
 *       当线程在执行同步代码块时，如果线程时间片结束，线程会暂停执行，但是不会释放锁，等待再次获取执行权后继续执行
 *       synchronized (account) {
 *           TODO：
 *           account.increase();
 *       }
 */
class Account{
    private int money = 0 ;

    public Account() {
    }

    public void increase(){
        money++;
    }
    public void decrease(){
        money--;
    }

    @Override
    public String toString() {
        return "Account{" +
                "money=" + money +
                '}';
    }
}
class IncreaseThread extends Thread {
    private Account account;
    public IncreaseThread(Account account) {
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

class DecreaseThread extends Thread {
    private Account account;

    public DecreaseThread(Account account) {
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

