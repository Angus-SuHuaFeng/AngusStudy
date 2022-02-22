package day28;
class Repository{
    private int count = 0;

    public Repository(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

//    生产者调用
    public void increase(){
        count++;
    }
//    消费者调用
    public void decrease(){
        count--;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "count=" + count +
                '}';
    }
}

class Product extends Thread{
    private Repository repository;
    public Product(Repository repository) {
        this.repository = repository;
    }
    @Override
    public void run() {
        while (true){

            synchronized (repository) {
//                当产品数量达到最大，则停止生产，等待消费者去消费
                while (repository.getCount() == 100){
//                当前线程放入repository对象的等待集中，此时当前线程处于等待状态
                    try {
                        repository.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                repository.increase();
//                notifyAll唤醒所有的在等待线程
                repository.notifyAll();
            }
        }
    }
}

class Consumer extends Thread{
    private Repository repository;

    public Consumer(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true){

            synchronized (repository) {
//            当产品数量等于0时，消费者等待生产者生产
//                条件判断要用while而不是if，如果在多个生产者消费者中，可能会发生虚假唤醒，
//                因为notify是随机唤醒，所以唤醒的可能是生产者而不是消费者（此时容量已满，需要消费者消费）
//                如果是while的话会重复判断，如果此时需要消费者，而被唤醒的是生产者，则通过while循环还可以使其睡眠，直到唤醒的是消费者为止
                while (repository.getCount() == 0){
//                当前线程放入repository对象的等待集中，此时当前线程处于等待状态
                    try {
                        repository.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                repository.decrease();
                repository.notifyAll();
            }
        }
    }
}

/**
 *  * 调用wait和notify/notifyAll方法必须要获取对应对象的锁
 *  * wait方法应该放入while循环中，以此来避免虚假唤醒
 *  * notify和notifyAll都可以唤醒线程。但只有当前线程退出同步代价块，释放锁后，其他线程才能进入同步代码块
 *
 */
public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        Repository repository = new Repository(0);
//        Product product = new Product(repository);
//        Consumer consumer = new Consumer(repository);
//        product.setName("Product");
//        consumer.setName("Consumer");
//        product.start();
//        consumer.start();
//        while (true){
//            Thread.sleep(100);
//            System.out.println(repository);
//        }
//

//        多生产者消费者模式（判断条件需要用while）
        for (int i = 0; i < 10; i++) {
            Product product = new Product(repository);
            Consumer consumer = new Consumer(repository);
            product.setName("Product" + i+1);
            consumer.setName("Consumer" + i+1);

            product.start();
            consumer.start();

        }
        while (true){
            Thread.sleep(100);
            System.out.println(repository);
        }
    }
}
