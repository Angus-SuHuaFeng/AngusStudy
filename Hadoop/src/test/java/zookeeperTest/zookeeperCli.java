package zookeeperTest;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class zookeeperCli {
    private static String connectString = "BigData1:2181,BigData2:2181,BigData3:2181";
    private static int sessionTimeOut = 4000;
    private ZooKeeper zkCli = null;
    @Before
    public void getClient() throws IOException, InterruptedException, KeeperException {
        zkCli = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
//                收到事件通知后
                System.out.println(watchedEvent.getType()+"---"+watchedEvent.getPath());
            }
        });
//        创建节点
//        zkCli.create("/SHF", "111".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
    @Test
//        监控节点
    public void watchId() throws InterruptedException, KeeperException {
        List<String> children = zkCli.getChildren("/", true);
        for (String child: children) {
            System.out.println(child);
        }

//        延迟阻塞
        Thread.sleep(Integer.MAX_VALUE);
    }
    @Test
    public void isExists() throws InterruptedException, KeeperException {
        Stat stat = zkCli.exists("/IDE", false);
        System.out.println(stat==null?"not Exist":"Exist");
    }


}
