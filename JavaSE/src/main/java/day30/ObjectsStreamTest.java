package day30;

import java.io.*;

public class ObjectsStreamTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String filename = "C:\\Users\\Angus\\Desktop\\Out.txt";
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(filename)));
        objectOutputStream.writeObject(new Person1("张三","男",20));
        objectOutputStream.writeObject("dwa");
        objectOutputStream.flush();
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(filename)));
        System.out.println(objectInputStream.readObject());
        System.out.println(objectInputStream.readObject());
        objectOutputStream.close();
    }
}
class Person1 implements Serializable{
    String name;
    String gender;
    int age;

    public Person1(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person1{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}
