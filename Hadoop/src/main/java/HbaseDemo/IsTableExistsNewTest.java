package HbaseDemo;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author Angus
 *      判断表是否存在
 */
public class IsTableExistsNewTest {
    public static Configuration conf;
    public static Admin admin;
    public static Connection connection;
    //    使用静态代码块提前加载
    static{
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
    }

    public static void main(String[] args) throws IOException {
//        dropTable("apiTest");
//        createTable("apiTest","INFO");
//        putData("apiTest","4","INFO","name","Su");
//        deleteRows("apiTest","1");
        scanTable("city");
//        getRow("apiTest","1");
    }

    /**
     *  新版API
     *      判断表是否存在
     */
    public static boolean isTableExists(String tableName) throws IOException {
        connection = ConnectionFactory.createConnection(conf);
//        创建客户端用户
        admin = connection.getAdmin();
//        调用方法
        boolean isExists = admin.tableExists(TableName.valueOf(tableName));
        close(connection,admin);
        return isExists;
    }

    /**
     *  创建表
     * @param tableName         创建的table名
     * @param columnFamily      列簇
     */

    public static void createTable(String tableName, String... columnFamily) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (isTableExists(tableName)){
            System.out.println(tableName+"表已存在");
        }else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            for (String col:columnFamily){
                hTableDescriptor.addFamily(new HColumnDescriptor(col));
            }
            admin.createTable(hTableDescriptor);
            System.out.println(tableName+"创建成功");
        }
    }

    public static void dropTable(String tableName) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (isTableExists(tableName)){
            admin.disableTable(tableName);
            admin.deleteTable(TableName.valueOf(tableName));
            System.out.println( tableName + "删除成功！");
        }else {
            System.out.println(tableName + "不存在！");
        }
    }

    public static void putData(String tableName,
                               String rowKey,
                               String columnFamily,
                               String column,
                               String value) throws IOException {
//        旧版创建hTable对象
//        @Cleanup HTable hTable = new HTable(conf, tableName);
//        新版
        Table hTable = connection.getTable(TableName.valueOf(tableName));
//        创建put对象封装数据
        Put put = new Put(rowKey.getBytes(StandardCharsets.UTF_8));
//        向put对象插入数据
        put.addColumn(columnFamily.getBytes(StandardCharsets.UTF_8), column.getBytes(StandardCharsets.UTF_8),
                value.getBytes(StandardCharsets.UTF_8));
        hTable.put(put);
        System.out.println("数据插入成功");
    }

    public static void deleteRows(String tableName, String... rows) throws IOException {
        @Cleanup Table hTable = connection.getTable(TableName.valueOf(tableName));
//        创建Delete集合
        ArrayList<Delete> deletes = new ArrayList<>();
        for (String row:rows){
//            创建delete对象
            Delete delete = new Delete(row.getBytes(StandardCharsets.UTF_8));
//            添加到集合中
            deletes.add(delete);
        }
//        删除
        hTable.delete(deletes);
        System.out.println("数据删除成功");
    }

    @SneakyThrows
    public static void scanTable(String tableName){
        @Cleanup HTable hTable = new HTable(conf,tableName);
//        创建扫描器对象
        Scan scan = new Scan();
//        获取对应表的扫描器
        ResultScanner scanner = hTable.getScanner(scan);
//        遍历扫描到的数据
        for (Result result:scanner){
//            遍历cells数组
            Cell[] cells = result.rawCells();
            for (Cell cell:cells){
                System.out.println(Bytes.toString(CellUtil.cloneRow(cell))+"\t"+
                Bytes.toString(CellUtil.cloneFamily(cell))+ "\t"+ Bytes.toString(CellUtil.cloneQualifier(cell))+"\t"+Bytes.toString(CellUtil.cloneValue(cell))+"\t"+cell.getTimestamp());
            }
        }
    }

    @SneakyThrows
    public static void getRow(String tableName, String rowKey){
        @Cleanup HTable hTable = new HTable(conf, tableName);
//        创建get对象
        Get get = new Get(rowKey.getBytes(StandardCharsets.UTF_8));
//        得到结果集
//        get到对应表的结果数据
        Result result = hTable.get(get);
        for (Cell cell : result.rawCells()){
            System.out.println("rowKey: " + Bytes.toString(result.getRow())+"\t"+
            "列簇: "+ Bytes.toString(CellUtil.cloneFamily(cell)) + "\t" +
                    "列名: "+ Bytes.toString(CellUtil.cloneQualifier(cell))+"\t"+
                        "值: "+ Bytes.toString(CellUtil.cloneValue(cell))+ "\t"+
                            "时间戳: "+cell.getTimestamp());
        }
    }
    public static void close(Connection connection, Admin admin){
        try {
            if (admin!=null){
                admin.close();
            }
            if (connection!=null){
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
