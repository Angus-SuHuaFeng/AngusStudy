package HDFS;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.jar.Pack200;

/**
 * @author Angus
 */
public class IoHdfs {
    static FileSystem fileSystem = null;
    static {
        Configuration conf = new Configuration();
//        conf.set("fs.defaultFS", "hdfs://BigData1:9000");
        try {
            //final URI uri , final Configuration conf , String user
            /**
             * URI : hadoop集群连接的地址:hdfs://BigData1:9000
             * Configuration : conf
             * user : root
             *
             * 用URI方式就无须conf.set()
             */
//            fileSystem = FileSystem.get(conf);
            fileSystem = FileSystem.get(new URI("hdfs://BigData1:9000"), conf, "root");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //IO流上传文件
        putFileToHdfs();
//        readFileSeek1();
//        readFileSeek2();
    }

    private static void readFileSeek1() throws IOException {
        //创建hdfs输入流
        FSDataInputStream fis = fileSystem.open(new Path("hdfs://BigData1:9000/data1/mysql-installer-community-8.0.18.0.msi"));
        //创建java输出流
        FileOutputStream fos = new FileOutputStream("E:\\HadoopDownload\\mysql-installer-community-8.0.18.0.msi.seek1");

        /*
         *  拷贝流(流对接)
         *  创建一个buffer
         */
        byte[] buffer = new byte[1024];
        for (int i = 0; i < 128 * 1024; i++) {
            fis.read(buffer);
            fos.write(buffer);
        }
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
        System.out.println("seek1下载成功");
    }

    private static void readFileSeek2() throws IOException {
        //创建hdfs输入流
        FSDataInputStream fis = fileSystem.open(new Path("hdfs://BigData1:9000/data1/mysql-installer-community-8.0.18.0.msi"));
        //创建java输出流
        FileOutputStream fos = new FileOutputStream("E:\\HadoopDownload\\mysql-installer-community-8.0.18.0.msi.seek2");
        //设定偏移量offset
        fis.seek(128*1024*1024);
        //流对接
        IOUtils.copyBytes(fis,fos,1024);
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
        System.out.println("seek2下载成功");
    }

    private static void putFileToHdfs() throws IOException {
        //创建输入流
        FileInputStream fis = new FileInputStream(new File("E:\\data1\\test.txt"));
        //创建输出流(返回输出流): fileSystem.create()
        FSDataOutputStream fos = fileSystem.create(new Path("hdfs://BigData1:9000/data1/text.txt"));
        //拷贝流
        IOUtils.copyBytes(fis,fos,128 * 1024,true);
        System.out.println("上传成功");
    }



}
