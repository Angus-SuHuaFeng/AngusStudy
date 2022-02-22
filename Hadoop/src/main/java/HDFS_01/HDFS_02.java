package HDFS_01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//下载文件
public class HDFS_02 {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://BigData1:9000"), conf, "root");
        fs.copyToLocalFile(false,new Path("/directory/app-20200818190450-0000"),new Path("E:\\HadoopDownload\\app-20200818190450-0000"),true);
        fs.close();
        System.out.println("下载成功");
    }
}
