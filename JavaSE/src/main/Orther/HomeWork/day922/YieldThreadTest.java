package HomeWork.day922;

import java.util.Date;

public class YieldThreadTest {
    public static void main(String[] args) {
        boolean flag1 = true;
        boolean flag2 = false;
        YieldThread yieldThread1 = new YieldThread(flag1);
        yieldThread1.setName("thread1");
        YieldThread yieldThread2 = new YieldThread(flag2);
        yieldThread2.setName("thread2");
        yieldThread1.start();
        yieldThread2.start();

    }
}
class YieldThread extends Thread{
    boolean flag = false;

    public YieldThread(boolean flag) {
        this.flag = flag;
    }
    @Override
    public void run() {
        long start = new Date().getTime();
        for (int i = 0; i < 100; i++) {
            if (flag){
                Thread.yield();
            }
            System.out.println(Thread.currentThread().getName()+"\t"+i);
        }
        long end = new Date().getTime();
        System.out.println(this.getName()+"运行时间: "+(end-start)+"ms");
        System.out.println(this.getName()+" is over");
    }
}

