package day30;

import java.io.*;

public class PrintWriteTest {
    public static void main(String[] args) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        try {
            fileReader = new FileReader("C:\\Users\\Angus\\Desktop\\Out.txt");
            bufferedReader = new BufferedReader(fileReader);
            fileWriter = new FileWriter("C:\\Users\\Angus\\Desktop\\Out1.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
//            包装字符输出流
            printWriter = new PrintWriter(bufferedWriter);
            String str;
            while ((str = bufferedReader.readLine())!=null){
                printWriter.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
