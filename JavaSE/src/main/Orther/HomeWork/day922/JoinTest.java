package HomeWork.day922;

/**
 *      同一个线程start在前join在后
 *      start是启动该线程
 *      join的话则是将该线程加入到调用线程（一般为主线程） 等该线程结束后 调用线程才会继续运行。
 *      如果没start，那么该线程还不存在，调用join毫无意义（也调用不了）！！
 */
public class JoinTest {
    public static void main(String[] args) {
        JoinThread joinThread = new JoinThread();
        joinThread.start();
        try {
            joinThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("main: "+i);
        }
    }
}
class JoinThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"\t"+i);
        }
    }
}
