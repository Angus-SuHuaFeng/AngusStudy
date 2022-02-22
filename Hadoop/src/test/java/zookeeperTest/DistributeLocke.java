package zookeeperTest;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class DistributeLocke {
    static int count = 10;
    public static void main(String[] args) {
//        定义客户端： 1.集群连接地址  2.重试策略  3.创建客户端
        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(1000, 10);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("BigData1:2181,BigData2:2181,BigData3:2181")
                .retryPolicy(policy)
                .build();
        client.start();

        final InterProcessMutex lock = new InterProcessMutex(client, "/locker");
        for (   int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public synchronized void run() {
                    try {
//                        获取锁，同一级的代码就会被锁住
                        lock.acquire();
                        PrintCountSum();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
//                            释放锁
                            lock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }

    private static void PrintCountSum() throws InterruptedException {
        count--;
        System.out.println("剩余产品数：" + count);
        Thread.sleep(500);
    }
}
