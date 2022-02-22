package HDFS_01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//IO流实现下载
public class HDFS_04 {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://BigData1:9000"), conf, "root");
        FileOutputStream fis = new FileOutputStream(new File("/data/B"));
        Path path = new Path("E:\\HadoopDownload\\B");
        FSDataOutputStream fos = fs.create(path);


    }
}
