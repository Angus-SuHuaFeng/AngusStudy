package day30;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest {
    public static void main(String[] args) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("C:\\Users\\Angus\\Desktop\\Out.txt","rw");
            Person p1 = new Person(1, "zahng", 21);
            Person p2 = new Person(2, "zahng", 21);
            Person p3 = new Person(3, "zahng", 21);
            p1.writePerson(randomAccessFile);
            System.out.println(randomAccessFile.length());
            p2.writePerson(randomAccessFile);
            System.out.println(randomAccessFile.length());
            p3.writePerson(randomAccessFile);
            System.out.println(randomAccessFile.length());


//            写入结束后，文件指针在文件末尾，所 以要重置文件偏移量
            randomAccessFile.seek(0);
            Person person = new Person();
            for (long i = 0; i <randomAccessFile.length(); ){
                person.readPerson(randomAccessFile);
                i=randomAccessFile.getFilePointer();
                System.out.println(person+",i="+i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert randomAccessFile != null;
                    randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
class Person{
    int id;
    String name;
    int age;


    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Person() {
    }
    public void writePerson(RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.writeInt(this.id);
        randomAccessFile.writeUTF(this.name);
        randomAccessFile.writeInt(this.age);
    }
    public void readPerson(RandomAccessFile randomAccessFile) throws IOException {
        this.id = randomAccessFile.readInt();
        this.name = randomAccessFile.readUTF();
        this.age = randomAccessFile.readInt();

    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}