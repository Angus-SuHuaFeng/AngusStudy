package HomeWork;

import java.io.*;

/**
 *   报EOFException的原因：
 *         1. 经测试发现，FileOutputStream每次实例化对象时会重置文件，将append改为true就可以使文件原有数据保留下来
 *         2. 初始没有文件，第一次运行后，创建了文件并写入了数据10000L
 *         3. 当第二次运行时，由于再次实例化了FileOutputStream对象，并且append属性为false，所以文件里上次写入的数据被清除了
 *         4. 此时文件是空的，DataInputStream调用readLong()读取数据时读取不到，所以报了EOFException异常
 */
public class DataStreamHomeWork {
    public static void main(String[] args) throws IOException {
        File file = new File(".\\test.txt");
        boolean exists = file.isFile();
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file,true)));
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        if (!exists){
            dataOutputStream.writeLong(10000L);
            dataOutputStream.flush();
        }else {
            dataInputStream.skipBytes((int) (file.length()-8));
            long l = dataInputStream.readLong()+1;
            System.out.println(l);
            dataOutputStream.writeLong(l);
            dataOutputStream.flush();
        }
        dataOutputStream.close();
        dataInputStream.close();
    }
}

