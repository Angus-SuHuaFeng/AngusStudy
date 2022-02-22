package HomeWork;

import java.util.*;

public class TreeMapTest {
    public static void main(String[] args) {
        TreeMap<Student3, String> tMap = new TreeMap<Student3, String>();
        Student3 s1 = new Student3(1, "su", 20);
        Student3 s2 = new Student3(2, "zhangsan", 21);
        Student3 s3 = new Student3(3, "lisi", 21);
        Student3 s4 = new Student3(4, "wangwu",19);

        tMap.put(s1,"15547477777");
        tMap.put(s2,"12321481284");
        tMap.put(s3,"15564717231");
        tMap.put(s4,"14551232145");
        Set<Map.Entry<Student3, String>> entries = tMap.entrySet();
        for (Map.Entry<Student3,String> entry: entries) {
            System.out.println(entry.getKey() + "\tValue: " + entry.getValue());
        }

    }
}
class Student3 implements Comparable<Student3>{
    int id;
    String name;
    int age;

    public Student3(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && age == student.age && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public int compareTo(Student3 o) {
        return this.id < o.id ? -1 : 1;
    }
}
