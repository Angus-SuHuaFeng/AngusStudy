package day30;

import java.io.*;

public class PrintStreamTest {
    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        PrintStream printStream = null;

        try {
//            实例化节点流（字节流）对象
            fis = new FileInputStream("C:\\Users\\Angus\\Desktop\\1.jpg");
            fos = new FileOutputStream("C:\\Users\\Angus\\Desktop\\2.jpg");
//            包装流对象
            bufferedInputStream = new BufferedInputStream(fis);
            bufferedOutputStream = new BufferedOutputStream(fos);
            printStream = new PrintStream(bufferedOutputStream);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = bufferedInputStream.read(buffer,0,1024)) != -1){
                System.out.println(len);
               printStream.println(new String(buffer,0,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            关闭流
            try {
                if (fos != null)
                    fos.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
