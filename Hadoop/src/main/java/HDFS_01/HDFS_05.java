package HDFS_01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFS_05 {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://Bigdata1:9000"), conf, "root");
        FSDataOutputStream fos = fs.create(new Path("/data/mySQL笔记.TXT"));
        fos.write("老师你集群好像关了".getBytes("utf-8"));
        fos.close();
        System.out.println("写入完成");

    }
}
