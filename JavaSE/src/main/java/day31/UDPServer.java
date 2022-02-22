package day31;

import sun.nio.cs.ext.GBK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * 服务端：
 *     第一版：
 *          实现了多客户端与服务端的一对一通信
 *          问题：
 *          1.中文无法正常显示
 *          2.客户端发送一句服务端必须回一句
 *          3.没有实现客户端之间通信
 */
public class UDPServer {
    public static void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(6789);
        UDPServerThread udpServerThread = new UDPServerThread(datagramSocket);
        Thread ServerThread = new Thread(udpServerThread);
        ServerThread.start();

    }
}
class UDPServerThread implements Runnable{
    DatagramSocket datagramSocket;
    byte[] bytes = new byte[1024];
    String info = null;
    DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
    public UDPServerThread(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run() {
        while (true) {
            /**
             * 接收客户端的数据
             */
            try {
                datagramSocket.receive(datagramPacket);
                info = new String(bytes, 0, datagramPacket.getLength(),StandardCharsets.UTF_8);
                System.out.println(info);
                System.out.println("收到客户端"+datagramPacket.getAddress() + ":"+datagramPacket.getPort()+"发来的数据：" + info);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /**
             * 响应客户端的数据
             */
            try {
                String bye = "BYE";
                if (info.equals("BYE")){
                    datagramSocket.send(new DatagramPacket(
                            bye.getBytes(StandardCharsets.UTF_8),
                            bye.length(), datagramPacket.getAddress(),
                            datagramPacket.getPort()));
                    datagramSocket.close();
                    System.exit(0);
                }else {
                    byte[] reBytes = new byte[1024];
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    String reInfo = bufferedReader.readLine();
                    System.out.println(reInfo);
                    datagramSocket.send(new DatagramPacket(
                            reInfo.getBytes(StandardCharsets.UTF_8),
                            reInfo.length(),
                            datagramPacket.getAddress(),
                            datagramPacket.getPort()));
                    System.out.println("已发送包给"+datagramPacket.getAddress()+":"+datagramPacket.getPort());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}



