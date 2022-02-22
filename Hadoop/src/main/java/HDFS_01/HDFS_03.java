package HDFS_01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
//IO流实现上传
public class HDFS_03 {
    public static void main(String[] args) throws Exception {
        //1. 创建配置文件信息
        Configuration conf = new Configuration();
        //2. 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://BigData1:9000"), conf, "root");
        //3. 创建输入流
        FileInputStream fis = new FileInputStream(new File("E:\\HadoopDownload\\test"));
        //4. 输出路径
        Path path = new Path("/data1/B");
        FSDataOutputStream fos = fs.create(path);
        //5. 流接收
        IOUtils.copyBytes(fis,fos,4*1024,false);
        //6. 关流
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        System.out.println("上传成功");
    }
}
