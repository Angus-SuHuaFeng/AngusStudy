package day26;

import java.util.HashSet;
import java.util.Objects;

/**
 * @author Angus
 *
 * Set接口继承了Collection接口中的所有方法
 *       Set：
 *           1.不能添加重复元素
 *           2.Set是无序集合
 */

public class HashSetTest {
    public static void main(String[] args) {
        /**
         *  HashSet 实现了Set接口，不允许储存相同元素，依赖于equals方法(判断是否存在该元素)添加元素
         */

        HashSet<String> set = new HashSet<>();
        set.add("ZQQ");
        set.add("SHF");
        set.add("SYN");
        Boolean flag = set.add("ZQQ");
        System.out.println(flag);
        for (String s : set) {
            System.out.println(s);
        }

        Student s1 = new Student(1, "小蓝", 17);
        Student s2 = new Student(2, "小明", 19);
        Student s3 = new Student(3, "小紫", 20);
        Student s4 = new Student(1, "小蓝", 17);
        HashSet<Student> set1 = new HashSet<>();
        System.out.println(set1.add(s1));
        System.out.println(set1.add(s2));
        System.out.println(set1.add(s3));
        System.out.println(set1.add(s4));

        for (Student student : set1) {
            System.out.println(student);
        }
    }
}
class Student{
    private int sid;
    private String name;
    private int age;

    public Student() {
    }

    public Student(int sid, String name, int age) {
        this.sid = sid;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return sid == student.sid && age == student.age && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid, name, age);
    }
}