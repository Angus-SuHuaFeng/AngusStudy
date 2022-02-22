package HbaseDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class HBaseUtils {
    public static Configuration conf;
    public static Admin admin;
    public static Connection connection;


    public static Admin HBaseFactory(){
        //使用HBaseConfiguration的单例方法实例化
//        创建配置文件对象
        conf = HBaseConfiguration.create();
//        设置配置
        conf.set("hbase.zookeeper.quorum", "BigData1");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("zookeeper.znode.parent", "/hbase");
//        创建客户端用户
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admin;
    }


}
