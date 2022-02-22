package CompressTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;


import java.io.*;

/**
 * @author Angus
 */
public class CompressTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        myCompression("C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\200111MR案例\\log.txt","org.apache.hadoop.io.compress.BZip2Codec");
        myDecompression("C:\\Users\\Angus\\Desktop\\第二阶段：离线开发\\第二阶段：离线开发\\200111MR案例\\log.txt.bz2");
    }


//    压缩
    private static void myCompression(String filename, String codeType) throws IOException, ClassNotFoundException {
//        创建输入流
        FileInputStream fis = new FileInputStream(new File(filename));
//        通过反射编码器解码器的类
        Class<?> codeClass = Class.forName(codeType);
//        通过反射找到解码器对象
        CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(codeClass, new Configuration());
//        创建输出流
        FileOutputStream fos = new FileOutputStream(new File(filename+codec.getDefaultExtension()));
//        解码器输出对象
        CompressionOutputStream cos = codec.createOutputStream(fos);
//        流拷贝
        IOUtils.copyBytes(fis, cos, 10*1024*1024, false);
//        关流
        cos.close();
        fis.close();
        fos.close();
        System.out.println("压缩成功");
    }
//    解压缩
    private static void myDecompression(String filename) throws IOException {
//        获取factor实例
        CompressionCodecFactory factory = new CompressionCodecFactory(new Configuration());
//        通过工厂类对象获取解码器
        CompressionCodec codec = factory.getCodec(new Path(filename));
//        获取输入流
        FileInputStream fis = new FileInputStream(new File(filename));
//        将输入流放入解码器解码
        CompressionInputStream cis = codec.createInputStream(fis);
//        获取输出流
        FileOutputStream fos = new FileOutputStream(new File(filename + ".decode"));
//        拷贝流数据
        IOUtils.copyBytes(cis,fos,10*1024*1024);

    }
}

