package day30;

import java.io.*;

public class DataStreamTest {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\Angus\\Desktop\\Out.txt");
        if (!file.exists()){
            file.createNewFile();
        }
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        dos.writeInt(1);
        dos.writeBoolean(true);
        dos.writeUTF("Java是最好的语言");
//        输出流写入文件后要刷新一下，这样输入流才可以从文件中得到数据
        dos.flush();
        System.out.println(dis.readInt());
        System.out.println(dis.readBoolean());
        System.out.println(dis.readUTF());
        dos.close();
        dis.close();
    }
}
