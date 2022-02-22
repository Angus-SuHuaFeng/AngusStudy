package HomeWork.BankTest;

public class BankTest {
    public static void main(String[] args) {
        BankAccount bankAccount1 = new BankAccount("156294632", 10000);
        BankThread bankThread = new BankThread(bankAccount1);
        Thread thread1 = new Thread(bankThread);
        Thread thread2 = new Thread(bankThread);
        Thread thread3 = new Thread(bankThread);
        Thread thread4 = new Thread(bankThread);
        Thread thread5 = new Thread(bankThread);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}