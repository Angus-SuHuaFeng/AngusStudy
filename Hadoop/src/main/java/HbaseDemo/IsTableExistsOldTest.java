package HbaseDemo;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

/**
 * @author Angus
 *      判断表是否存在
 */
public class IsTableExistsOldTest {
    public static Configuration conf;
    static{
        //使用HBaseConfiguration的单例方法实例化
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "BigData1");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("zookeeper.znode.parent", "/hbase");
    }
    public static void main(String[] args) throws IOException {
        System.out.println(isTableExists("stu"));
        System.out.println(isTableExists("student"));
    }

    /**
     *  老版API
     */
    @SneakyThrows
    public static boolean isTableExists(String tableName){
        @Cleanup HBaseAdmin hBaseAdmin = new HBaseAdmin(conf);
        boolean isExists = hBaseAdmin.tableExists(tableName);
        return isExists;
    }
}
