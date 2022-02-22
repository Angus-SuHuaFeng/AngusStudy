package day31;

import sun.nio.cs.ext.GBK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;


public class UDPClient1 {
    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            UDPClientThread udpClientThread = new UDPClientThread(datagramSocket);
            Thread ClientThread = new Thread(udpClientThread);
            ClientThread.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}

class UDPClientThread implements Runnable{
    DatagramSocket datagramSocket;
    byte[] reBytes = new byte[1024];
    DatagramPacket datagramPacket = new DatagramPacket(reBytes, reBytes.length);
    public UDPClientThread(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run() {
        while(true){
            try {
                /**
                 * 向服务端发送数据
                 */
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String reInfo = bufferedReader.readLine();
                System.out.println(reInfo);
                DatagramPacket datagramPacket = new DatagramPacket(reInfo.getBytes(StandardCharsets.UTF_8), reInfo.length(), InetAddress.getByName("127.0.0.1"), 6789);
                datagramSocket.send(datagramPacket);
                System.out.println("已发送包给服务端");
            } catch (IOException e) {
                e.printStackTrace();
            }
            /**
             * 接收客户端响应的数据
             */
            try {

                datagramSocket.receive(datagramPacket);
                InetAddress Address = datagramPacket.getAddress();
                int port = datagramSocket.getPort();
                String info = new String(reBytes, 0, datagramPacket.getLength(),StandardCharsets.UTF_8);
                System.out.println(info);
                System.out.println("接收到"+datagramPacket.getAddress()+":"+datagramPacket.getPort()+"发送的数据:"+info);
                if (info.equals("BYE")){
                    datagramSocket.send(new DatagramPacket("BYE".getBytes(StandardCharsets.UTF_8),"BYE".length(), Address, port));
                    datagramSocket.close();
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}