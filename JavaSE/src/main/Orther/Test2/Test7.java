package Test2;

public class Test7 {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();
        new Thread(myThread).start();
    }
}
class MyThread implements Runnable{
    private int num;
    @Override
    public void run() {
        synchronized (this){
            for (int i=0;i<5;i++){
                num+=i;
                System.out.println(num);
            }
        }
    }
}