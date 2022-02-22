package Experiment1.ExTest;

import Experiment1.FTPClientThread;

public class FTPClient2 {
    public static void main(String[] args) {
        FTPClientThread ftpClientThread = new FTPClientThread();
        ftpClientThread.setName("客户端2");
        ftpClientThread.start();
    }
}