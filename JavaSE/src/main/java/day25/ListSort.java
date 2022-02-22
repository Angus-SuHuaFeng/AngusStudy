package day25;

import java.util.*;

/**
 * @author Angus
 */
public class ListSort {
    public static void main(String[] args) {
        /**
         * list排序:
         *  Collections.sort()可以对list进行排序
         *  前提是list元素实现了Comparable接口,依赖list元素实现了Comparable接口中的compareTo方法
         *  需要先给定排序规则
         *
         *  定义排序规则
         *  lambda表达式
         */


        List<String> list5 = new ArrayList<>();
        list5.add("one");
        list5.add("two");
        list5.add("three");
        System.out.println(list5);

        //对集合进行排序
        Collections.sort(list5);
        System.out.println(list5);


        //对自定义类进行排序
        List<Student> sList = new ArrayList<>();
        Student s1 = new Student(001, "小红", 18);
        Student s2 = new Student(002, "小明", 19);
        Student s3 = new Student(003, "小紫", 20);
        Student s4 = new Student(004, "小蓝", 17);
        sList.add(s1);
        sList.add(s2);
        sList.add(s3);
        sList.add(s4);
        System.out.println(sList);
        Collections.sort(sList);
        System.out.println(sList);
        //第二种方法:在sort()中new一个Comparable
        Collections.sort(sList, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getSid()- o2.getSid();
            }
        });

        //java8引入了lambda表达式
        Collections.sort(sList,(o1,o2)->o1.getSid()- o2.getSid());
        System.out.println(sList);
    }
}
//自定义类实现Comparable接口
class Student implements Comparable<Student>{
    private int sid;
    private String name;
    private int age;

    public Student(int sid, String name, int age) {
        this.sid = sid;
        this.name = name;
        this.age = age;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
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
        return sid == student.sid &&
                age == student.age &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid, name, age);
    }


    @Override
    public int compareTo(Student o) {
        return this.age-o.age;
    }
}
