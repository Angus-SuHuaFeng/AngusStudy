package day28;

public class RunnableTest {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                }
            }
        });
        thread1.setName("线程1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);

                }
            }
        });
        thread2.setName("线程2");

        thread1.start();
        thread2.start();
    }
}
