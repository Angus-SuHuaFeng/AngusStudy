package day30;

import java.io.*;

public class FileReaderTest {
    public static void main(String[] args) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        File file = new File("C:\\Users\\Angus\\Desktop\\1.jpg");
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            fileWriter = new FileWriter("C:\\Users\\Angus\\Desktop\\2.jpg");
            bufferedWriter = new BufferedWriter(fileWriter);
            int len;
            char[] c = new char[1024];
            while ((len = bufferedReader.read(c,0,1024))!=-1){
                bufferedWriter.write(c,0, len);
                bufferedWriter.flush();
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