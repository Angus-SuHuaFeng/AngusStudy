package HDFS_01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
/**
 * @author Angus
 */ //上传文件
public class HDFS_01 {
    public static void main(String[] args) throws Exception {
        //1.创建配置对象
        Configuration conf = new Configuration();
        //2。设置参数
        conf.set("fs.defaultFS","hdfs://BigData1:9000");
        //3.获取系统，找到HDFS地址
        FileSystem fs = FileSystem.get(conf);
        //4。指定windows地址，文件路径
        Path scr = new Path("E:\\HadoopDownload\\test");
        //5.HDFS路径
        Path dest = new Path("/data1/test3");
        //6.拷贝方式
        fs.copyFromLocalFile(scr,dest);
        //7.关流
        fs.close();
        System.out.println("上传成功");
    }
}
