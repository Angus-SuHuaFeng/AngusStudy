package day27;

import java.util.Comparator;

/**
 * @author Angus
 *      类对象是为了服务与反射
 *      java中的所有类型（类，接口，数组，基本类型）都有类对象
 *      类对象包含了类型的信息
 */
public class ClassTest {
    public static void main(String[] args) throws ClassNotFoundException {
        //获取Student类的类对象
        //1.使用.class
        Class studentClass = Student.class;
        System.out.println(studentClass);
        //2.使用继承于Object类的实例方法getClass()方法
        Student student = new Student();
        Class aClass = student.getClass();
        //3.使用Class的静态方法Class.forName()
        Class c1 = Class.forName("day27.Student");

        System.out.println(studentClass == c1);
        System.out.println(aClass == c1);
        System.out.println(studentClass == aClass);

        //接口也有类对象
        Class comparableClass = Comparable.class;
        System.out.println(comparableClass);

        //基本类型也有对象
        Class<Integer> integerClass = int.class;
        System.out.println(integerClass);

        //数组也有对象
        Class<int[]> aClass1 = int[].class;
        System.out.println(aClass1);


    }
}

class Student{
    private int age;
    private String name;
    private String gender;

    public Student(int age, String name, String gender) {
        this.age = age;
        this.name = name;
        this.gender = gender;
    }

    public Student() {

    }

    public Student(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
