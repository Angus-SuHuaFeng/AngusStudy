package HomeWork;
import java.util.*;

public class TreeSetTest {
    public static void main(String[] args) {
        TreeSet<Student4> set = new TreeSet<Student4>();
        Student4 s1 = new Student4(1001,"zhangsan",23);
        Student4 s2 = new Student4(1002,"lisi",13);
        Student4 s3 = new Student4(1003,"wangwu",24);
        Student4 s4 = new Student4(1004,"zhaoliu",31);
        Student4 s5 = new Student4(1001,"zhangsan",23);
        set.add(s1);
        set.add(s2);
        set.add(s3);
        set.add(s4);
        set.add(s5);
        print(set);
    }
    public static void print(Collection c) {
        Iterator it = c.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

}
class Student4 implements Comparable<Student4>{
    int num;
    String name;
    int age;

    public Student4() {
    }

    public Student4(int num, String name, int age) {
        super();
        this.num = num;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student [num=" + num + ", name=" + name + ", age=" + age + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student4 student4 = (Student4) o;
        return num == student4.num && age == student4.age && name.equals(student4.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, name, age);
    }

    @Override
    public int compareTo(Student4 o) {
        return this.num < o.num ? -1 : 1;
    }
}