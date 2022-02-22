package HomeWork;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileStreamHomeWork {
    public static void main(String[] args) {
        File file = new File(".\\test.txt");
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        boolean exists = file.exists();
        if (exists){
            System.out.println("文件已存在");
            try {
                fileOutputStream = new FileOutputStream(file,true);
                fileOutputStream.write("HelloWorld\n".getBytes(StandardCharsets.UTF_8));
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (fileOutputStream!=null){
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            try {
                boolean newFile = file.createNewFile();
                if (newFile){
                    System.out.println("创建文件成功");
                    fileOutputStream = new FileOutputStream(file,true);
                    fileOutputStream.write("HelloWorld".getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (fileOutputStream!=null){
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            fileInputStream = new FileInputStream(file);
            int c;
            while ((c= fileInputStream.read())!=-1){
                System.out.print((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(file.getPath());
    }
}
