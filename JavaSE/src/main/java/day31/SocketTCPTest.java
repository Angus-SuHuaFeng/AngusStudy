package day31;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketTCPTest {
    public static void main(String[] args) {
        if (args.length>0){
            server();
        }else {
            client();
        }
    }
//    服务器端
    public static void server(){
        try {
//            创建服务器套接字Socket
            ServerSocket serverSocket = new ServerSocket(10200);
//            用来监听是否收到客户端的连接请求,连接请求到来之前程序一直在此阻塞,当有连接请求时，返回一个Socket套接字用来和客户端通信
            Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            System.out.println("server收到数据: " + new String(bytes,0,len));
            OutputStream outputStream = accept.getOutputStream();
            outputStream.write("How are you!".getBytes(StandardCharsets.UTF_8));
            outputStream.close();
            inputStream.close();
            accept.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//    客户端
    public static void client(){
        try {
            Socket localhost = new Socket("localhost", 10200);
            OutputStream outputStream = localhost.getOutputStream();
            outputStream.write("Hello,This is localhost!".getBytes(StandardCharsets.UTF_8));
            InputStream inputStream = localhost.getInputStream();
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            System.out.println("client收到数据: " + new String(bytes,0,len));
            outputStream.close();
            inputStream.close();
            localhost.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}