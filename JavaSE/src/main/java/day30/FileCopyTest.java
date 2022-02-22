package day30;

import java.io.*;

public class FileCopyTest {
    public static void main(String[] args) throws IOException {
        args = new String[]{"C:\\Users\\Angus\\Desktop\\1.jpg", "C:\\Users\\Angus\\Desktop\\2.jpg"};
//        第一种读写流方法
//        实例化节点流对象
      FileInputStream fis = new FileInputStream(args[0]);
      FileOutputStream fos = new FileOutputStream(args[1]);
      int c = 0;
      while ((c = fis.read())!=-1){
          fos.write(c);
          fos.flush();
      }
      fos.close();
      fis.close();


//        第二种读写流的方法
//        FileInputStream fis = new FileInputStream(args[0]);
//        FileOutputStream fos = new FileOutputStream(args[1]);
//        byte[] bytes = new byte[1024];
//        int len;
//        while ((len = fis.read(bytes))!=-1){
//            fos.write(bytes,0, len);
//            fos.flush();
//        }
//      fos.close();
//      fis.close();
//        第三种方法，BufferedOutputStream 缓冲流
//        实例化包装流对象
//        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(args[0]));
//        BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(args[1]));
//        int c = 0;
//        while ((c = fis.read())!=-1){
//            fos.write(c);
//        }
//        fos.close();
//        fis.close();
    }
}

