package day31;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  此程序实现了点对点的聊天，必须你说一句我说一句，如果连着说就会阻塞
 */
public class ChatTest {
    public static void main(String[] args) throws IOException {
        if (args.length>0){
            server();
        }else {
            client();
        }
    }

    public static void server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10200);
        Socket accept = serverSocket.accept();
//        写
        OutputStream outputStream = accept.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

//        读
        InputStream inputStream = accept.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        while (true){
//            读
            str = dataInputStream.readUTF();    //读这里会阻塞，等待对方输入，否则一直阻塞直到对方输入为止
            System.out.println("对方说: "+str);
            if (str.equals("bye")){
                System.out.println("对方说再见，聊天结束");
                break;
            }
//            写
            str = bufferedReader.readLine();
            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();
            if (str.equals("bye")){
                System.out.println("再见，聊天结束");
                break;
            }
        }
        bufferedReader.close();
        bufferedInputStream.close();
        bufferedOutputStream.close();
        accept.close();
        serverSocket.close();
    }
    public static void client() throws IOException {
        Socket Socket = new Socket("localhost",10200);
//        写
        OutputStream outputStream = Socket.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

//        读
        InputStream inputStream = Socket.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        while (true){
//            写
            str = bufferedReader.readLine();
            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();
            if (str.equals("bye")){
                System.out.println("再见，聊天结束");
                break;
            }
//            读
            str = dataInputStream.readUTF();
            System.out.println("对方说: "+str);
            if (str.equals("bye")){
                System.out.println("对方说再见，聊天结束");
                break;
            }
        }
        bufferedReader.close();
        bufferedInputStream.close();
        bufferedOutputStream.close();
        Socket.close();
    }
}
