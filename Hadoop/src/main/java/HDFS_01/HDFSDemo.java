package HDFS_01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
//将本地文件上传到HDFS文件系统
public class HDFSDemo {
    public static void main(String[] args) throws IOException {

        //1.获取HDFS文件系统
        Configuration conf = new Configuration();
        //2.配置在集群上运行
        conf.set("fs.defaultFS","hdfs://BigData1:9000");
        FileSystem fileSystem = FileSystem.get(conf);
        //3.把本地文件上传到HDFS文件系统
        fileSystem.copyFromLocalFile(new Path("E:\\eula.1028.txt"),new Path("/data/eula.1028.txt"));
        //4.关闭资源
        fileSystem.close();
        System.out.println("复制完成");
    }
}
