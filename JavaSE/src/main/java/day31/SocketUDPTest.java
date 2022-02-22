package day31;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class SocketUDPTest {
    public static void main(String[] args) throws IOException {
        if (args.length>0){
            receiver();
        }else {
            sender();
        }
    }
    public static void receiver() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8889);
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        datagramSocket.receive(datagramPacket);
        System.out.println("接收到数据："+new String(bytes,0,datagramPacket.getLength()));
        String str = "How are you!";
        DatagramPacket datagramPacket1 = new DatagramPacket(str.getBytes(StandardCharsets.UTF_8), str.length(),
                datagramPacket.getAddress(), datagramPacket.getPort());
        datagramSocket.send(datagramPacket1);
        datagramSocket.close();
    }
    public static void sender() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        String str = "Hello,this is zhangsan";
        DatagramPacket datagramPacket1 = new DatagramPacket(str.getBytes(StandardCharsets.UTF_8), str.length(), InetAddress.getByName("127.0.0.1"), 8889);
        datagramSocket.send(datagramPacket1);
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        datagramSocket.receive(datagramPacket);
        System.out.println("接收到数据"+new String(bytes,0,datagramPacket.getLength()));
        datagramSocket.close();
    }
}