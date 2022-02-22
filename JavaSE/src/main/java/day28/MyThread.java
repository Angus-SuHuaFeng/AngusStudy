package day28;

public class MyThread extends Thread{
/**  线程一旦得到CPU执行权，那么他就会执行run方法中的代码块
 *   当run方法中的代码块执行完毕，线程死掉
 *   线程的run方法可能还没执行完，时间片就已经结束了，线程会记录它执行到哪一步，然后等待下一次调度
 *
 *   守护线程：
 *      使用.setDaemon方法设置为守护线程，守护线程会在其他所有线程执行完毕后结束运行
 *      例如java中的垃圾回收器GC就是一个守护线程，当其他线程运行完毕，不再产生垃圾，它也自然而然结束运行了
 *      其他线程都结束，守护线程立即结束，不会继续执行1
 */
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (isDaemon()){
                try {
                    System.out.println(Thread.currentThread().getName()+"是守护线程");
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else
            System.out.println(this.getName() + "正在运行" + ":" + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread thread1 = new MyThread();
        thread1.setName("线程1");
        MyThread thread2 = new MyThread();
        thread2.setName("线程2");
        thread2.setDaemon(true);
//        启动线程（并不是立即启动，而是等待系统调度），同一个线程只能start一次，只要start过后就会被标记成true
        thread1.start();
        thread2.start();
//        thread2.join();
//        这也是一个线程
//        for (int i = 0; i < 100; i++) {
//            Thread thread = Thread.currentThread();
//            System.out.println(thread.getName());
//        }
         
    }
}
