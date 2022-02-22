package day31;

import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPClient2 {
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
