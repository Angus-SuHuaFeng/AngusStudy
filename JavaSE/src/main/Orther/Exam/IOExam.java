package Exam;

import java.io.*;

public class IOExam {
    public static void main(String[] args) {
        File fileIn = new File("C:\\Users\\Angus\\Desktop\\journal.log");
        File fileOut = new File("C:\\Users\\Angus\\Desktop\\journal1.log");
        byteCopy(fileIn,fileOut);
    }

//    字节流
    public static void byteCopy(File fileIn, File fileOut){
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(fileIn));
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileOut));
            int len;
            byte[] bytes = new byte[1024];
            try {
                while ((len = bufferedInputStream.read(bytes))!=-1){
                    bufferedOutputStream.write(bytes,0,len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedOutputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    字符流
    public static void charCopy(File fileIn, File fileOut){
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileIn));
            bufferedWriter = new BufferedWriter(new FileWriter(fileOut));
            char[] chars = new char[1024];
            int len;
            while ((len = bufferedReader.read(chars))!=-1){
                bufferedWriter.write(chars,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedWriter.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

