package HomeWork.day922;

public class SellTicketSystemTest {
    public static void main(String[] args) {
        SellTicketThread sellTicketThread = new SellTicketThread();
        Thread thread1 = new Thread(sellTicketThread,"thread1");
        Thread thread2 = new Thread(sellTicketThread,"thread2");
        Thread thread3 = new Thread(sellTicketThread,"thread3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class SellTicketThread implements Runnable{
    private int tickets = 100;

    public synchronized boolean decreaseTickets() throws InterruptedException {
        if (tickets>0) {
            tickets--;
            System.out.println(Thread.currentThread().getName() + ":余票" + tickets);
            Thread.sleep(10);
            return true;
        }else
            System.out.println(Thread.currentThread().getName() + "票售空了");
            return false;
    }
    @Override
    public void run() {
        boolean flag = true;
        while (flag){
            synchronized (this){
                try {
                    flag = decreaseTickets();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
