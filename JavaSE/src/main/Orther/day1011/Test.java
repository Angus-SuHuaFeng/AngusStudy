package day1011;

import java.util.Random;

/**
 *  可以通过定义一个值，在多个线程中进行判断，
 */
public class Test{
    public static void main(String[] args) throws InterruptedException {
        Storage storage = new Storage();

        while (true){
            Counter counter = new Counter(storage);
            counter.start();
            Printer printer = new Printer(storage);
            printer.start();
            if (storage.num==99){
                break;
            }
        }
    }
}




class Storage {
    int num;

    public Storage(int num) {
        this.num = num;
    }

    public Storage() {
    }
}
class Counter extends Thread{
    Storage storage = null;

    public Counter(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        synchronized (storage){
            Random random = new Random();
            storage.num = random.nextInt(100);
            System.out.println("写入了数据："+storage.num);
            try {
                storage.wait();
                storage.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Printer extends Thread {
    Storage storage;

    public Printer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        synchronized (storage){
            System.out.println("读到数据："+storage.num);
            try {
                storage.wait();
                storage.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
