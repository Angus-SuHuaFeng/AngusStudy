package Experiment1;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 *  FTP客户端
 */
public class FTPClientThread extends Thread{
    private String host = "127.0.0.1";
    private int port = 8888;
    
    /**
     * 使用默认属性
     */
    public FTPClientThread() {
    }
    /**
     * @param host : 指定服务器地址
     * @param port : 指定服务端端口
     */
    public FTPClientThread(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private boolean connected = false;
    private Socket socket = null;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;
    private String localDir = "C:\\Users\\Angus\\Desktop\\FTPServerDir\\download\\";
    private String threadName = Thread.currentThread().getName();


    @Override
    public void run() {
        System.out.println("FTP Client启动...");
        connect();
        String command = null;
        while (true) {
            System.out.print("ftp:\\>");
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextLine();
            if (command.startsWith("put ")){
                put(command.substring(4));
            }else if (command.startsWith("get ")){
                get(command.substring(4));
            }else if (command.equals("exit")){
                break;
            }else {
                System.out.println("无效指令,请重试!");
            }
        }
        disconnect();
    }

    public void connect(){
        try {
            Socket socket = new Socket(host,port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("FTP服务器连接成功...");
            connected = true;
        } catch (ConnectException e) {
            System.out.println("连接FTP服务器失败...正在重新连接...");
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String localFile){
        try {
            File file = new File(localFile);
            if (file.exists()){
                long size = file.length();
                dataOutputStream.writeUTF("put "+file.getName());
                dataOutputStream.writeLong(size);
                FileInputStream fileInputStream = new FileInputStream(localFile);
                for (long i=0;i<size;i++){
                    dataOutputStream.write(fileInputStream.read());
                }
                fileInputStream.close();
                System.out.println("文件上传成功!");
            }else {
                System.out.println("文件不存在,上传失败!");
            }
        } catch (IOException e) {
            System.out.println("文件上传失败!");
        }
    }

    public void get(String remoteFile){
        try {
            dataOutputStream.writeUTF("get "+remoteFile);
            boolean exist = dataInputStream.readBoolean();
            if (exist){
                long size = dataInputStream.readLong();
                FileOutputStream fileOutputStream = new FileOutputStream(localDir + remoteFile,true);
                for (long i = 0; i < size; i++) {
                    fileOutputStream.write(dataInputStream.read());
                }

                fileOutputStream.close();
                System.out.println("下载文件成功!");
            }else {
                System.out.println("请求的文件不存在,请重新输入指令！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("下载文件失败!");
        }
    }

    public void disconnect(){
        if (connected){
            try {
                dataOutputStream.writeUTF("disconnect");
                dataInputStream.close();
                dataOutputStream.close();
                if (socket!=null){
                    socket.close();
                }
                connected = false;
                System.out.print("程序退出.");
            } catch (SocketException e) {
                System.out.print("网络连接异常,程序退出!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}