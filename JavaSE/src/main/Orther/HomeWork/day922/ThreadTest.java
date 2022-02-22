package HomeWork.day922;

public class ThreadTest {
    public static void main(String[] args) {
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();
        myThread1.setName("thread1");
        myThread2.setName("thread2");
        myThread1.start();
        myThread2.start();
    }
}
class MyThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"\t"+i);
        }
        System.out.println(this.getName()+" is over");
    }
}
