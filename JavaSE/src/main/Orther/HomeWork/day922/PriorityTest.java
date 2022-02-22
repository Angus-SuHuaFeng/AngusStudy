package HomeWork.day922;

public class PriorityTest {
    public static void main(String[] args) {
        System.out.println("max Priority: "+Thread.MAX_PRIORITY);
        System.out.println("min Priority: "+Thread.MIN_PRIORITY);
        System.out.println("norm Priority: "+Thread.NORM_PRIORITY);
        PriorityThread thread1 = new PriorityThread();
        thread1.setName("thread1");
        thread1.setPriority(10);
        PriorityThread thread2 = new PriorityThread();
        thread2.setName("thread2");
        thread2.setPriority(2);
        thread1.start();
        thread2.start();
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"\t"+i);
        }
        System.out.println("main is over");
    }
}
class PriorityThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"\t"+i);
        }
        System.out.println(this.getName()+" is over");
    }
}