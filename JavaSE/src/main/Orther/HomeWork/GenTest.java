package HomeWork;

import java.util.ArrayList;
import java.util.Objects;

public class GenTest {
    public static void main(String[] args) {
        ArrayList<Student> aList =new ArrayList<Student>();
        ArrayList<Student1> oList =new ArrayList<Student1>();

    }
}
class Student{
    int id;
    String name;
    int age;

    public Student(int id, String name, int age) {
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
}
class Student1 extends Student{
    String gender;
    public Student1(int id, String name, int age) {
        super(id, name, age);
    }

    public Student1(int id, String name, int age, String gender) {
        super(id, name, age);
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student1 student1 = (Student1) o;
        return Objects.equals(gender, student1.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gender);
    }
}