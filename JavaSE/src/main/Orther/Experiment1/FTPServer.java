package Experiment1;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *  FTP服务器
 */
public class FTPServer {
    private static Vector<Socket> sockets = new Vector<>();
    public static void main(String[] args) {
        System.out.println("FTP服务器启动...");
        ServerSocket serverSocket = null;
//      创建服务器
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                Socket accept = serverSocket.accept();
                //                sockets.add(accept);
                new TransHandler(accept).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
/**
 *  处理客户端请求
 */
class TransHandler extends Thread{
    //客户端的socket
    private Socket m_clientSocket;
    //从客户端传来的指令
    String command;
    //用于得到从socket端的输入信息
    DataInputStream m_dataInputStream;
    //用于向socket输出的输出流
    DataOutputStream m_dataOutputStream;
    //客户端的地址和端口
    InetAddress inetAddress ;
    int port ;
    //客户端下载地址
    String serverDir = "C:\\Users\\Angus\\Desktop\\FTPServerDir\\";

    public TransHandler(Socket accept) {
        //客户端的socket
        m_clientSocket = accept;
        inetAddress = accept.getInetAddress();
        port = accept.getPort();
        try {
            m_dataInputStream = new DataInputStream(accept.getInputStream());
            m_dataOutputStream = new DataOutputStream(accept.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(inetAddress+":\\"+ port+"连接成功");
            while(true){
                if (m_clientSocket.isConnected()){
                    command = m_dataInputStream.readUTF();
                    if (command.startsWith("put ")){
                        receive(command.substring(4),m_dataInputStream);
                    }else if (command.startsWith("get ")){
                        send(command.substring(4),m_dataOutputStream);
                    }else if (command.equals("disconnect")){
                        System.out.println(port+"已退出连接");
                        break;
                    }
                }
            }
        }catch (Exception e){
            System.out.println(port+"客户端异常退出");
        }finally {
            try {
                m_dataInputStream.close();
                m_dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void receive(String filename, DataInputStream dis){
        try {
            long size = dis.readLong();
            FileOutputStream fileOutputStream = new FileOutputStream(serverDir + filename);
            for (long i = 0; i < size; i++){
                fileOutputStream.write(dis.read());
            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(port+"文件传输失败");
        }
        System.out.println(port+"文件接收成功");
    }

    public void send(String filename, DataOutputStream dos){
        FileInputStream fileInputStream = null;
        try {
            File file = new File(serverDir + filename);
            if (!file.exists()){
                dos.writeBoolean(false);
            }else {
                dos.writeBoolean(true);
                long size = file.length();
                dos.writeLong(size);
                fileInputStream = new FileInputStream(file);
                for (long i=0;i<size;i++){
                    dos.write(fileInputStream.read());
                }
                fileInputStream.close();
                System.out.println(port +"文件发送成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(port + "文件发送失败");
        }
    }
}
