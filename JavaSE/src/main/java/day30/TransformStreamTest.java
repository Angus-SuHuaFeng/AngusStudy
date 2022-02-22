package day30;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TransformStreamTest {
    /**
     *  TransformStream: 字节流和字符流转换
     *      InputStreamReader实现了字节流转换成字符流
     *      OutputStreamWriter实现了字符流向字节流转换
     * @param args
     */
    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        try {
//            字节流
            fis = new FileInputStream("C:\\Users\\Angus\\Desktop\\1.jpg");
            fos = new FileOutputStream("C:\\Users\\Angus\\Desktop\\2.jpg");
//            转换成字符流
            inputStreamReader = new InputStreamReader(fis,StandardCharsets.UTF_16);
            outputStreamWriter = new OutputStreamWriter(fos,StandardCharsets.UTF_16);

//            读取字符流
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            String str = null;
            int len;
            char[] chars = new char[2];
//            while ((str = bufferedReader.readLine()) != null){
//                System.out.println(str);
//                bufferedWriter.write(str);
//                bufferedWriter.newLine();
//            }

            outputStreamWriter.flush();
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

