package day31;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatThreadTest {
    public static void main(String[] args) throws IOException {
        if (args.length>0){
            server();
        }else {
            client();
        }
    }
    public static void server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(6789);
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

        ReaderThread readerThread = new ReaderThread(dataInputStream);
        readerThread.start();
        WriteThread writeThread = new WriteThread(dataOutputStream);
        writeThread.start();

    }
    public static void client() throws IOException {
        Socket Socket = new Socket("localhost",6789);
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
        ReaderThread readerThread = new ReaderThread(dataInputStream);
        readerThread.start();
        WriteThread writeThread = new WriteThread(dataOutputStream);
        writeThread.start();

    }
}

class ReaderThread extends Thread{
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    public ReaderThread(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        String str = null;
        while (true){
//            读
            try {
                str = dataInputStream.readUTF();
                System.out.println("对方说: " + str);
                if (str.equals("BYE")){

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class WriteThread extends Thread{
    DataOutputStream dataOutputStream;

    public WriteThread(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        while (true){
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
//            写
            try {
                str = bufferedReader.readLine();
                dataOutputStream.writeUTF(str);
                dataOutputStream.flush();
                if (str.equals("bye")){
                    System.out.println("再见，聊天结束");
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
