package day31;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class URLTest {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.baidu.com/");
        InputStream inputStream = url.openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String str;
        while ((str = bufferedReader.readLine())!=null){
            System.out.println(str);
        }
        bufferedReader.close();
    }
}
