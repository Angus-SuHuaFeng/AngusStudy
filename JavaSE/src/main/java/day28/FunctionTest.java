package day28;

public class FunctionTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                    if (Thread.currentThread().isInterrupted()){
                        System.out.println(Thread.currentThread().getName()+"被中断了");
                        break;
                    }
                    try {
//                        sleep方法使程序睡眠一段时间
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread1.setName("线程1");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                    try {
//                        sleep方法使程序睡眠一段时间
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread2.setName("线程2");

//        thread1.start();

        try {
//            thread1.join方法可以使当前线程等待thread1执行结束再执行（让thread1先执行）
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程恢复执行");
        thread2.start();
//        直接杀死thread2线程
        thread2.stop();

        thread1.start();
        Thread.sleep(100);
//        中断线程，还可以再次继续执行(interrupt()不能中断在运行中的线程，它只能改变中断状态而已。),中断之后仍然会继续运行,只是改变了中断状态
        thread1.interrupt();

    }

}
