package day17Homework;

//给Person类增加一个静态字段count和静态方法getCount，统计实例创建的个数。
public class Test {
    public static void main(String[] args) {
        Person p1 = new Person("xiaoming");
        Person p2 = new Person("xiaowang");
        System.out.println(Person.getCount());

    }
}
class Person{
    String name;
    static int count=0;
    public Person(String name) {
        this.name = name;
        count++;
    }


    public static int getCount(){
        return count;
    }
}