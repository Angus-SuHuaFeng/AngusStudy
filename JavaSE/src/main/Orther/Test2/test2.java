package Test2;

public class test2 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Person xiaowang = new Person("xiaowang","nan",20);
        Person xiaoli   = new Person("xiaoli","nv",27);
        System.out.println(xiaowang.name);
        System.out.println(xiaowang.age);
        System.out.println(xiaoli.name);
        System.out.println(xiaoli.age);
        xiaowang =xiaoli;
        xiaowang.setName("xiaowang1-1");
        System.out.println(xiaowang.name);
        System.out.println(xiaowang.age);
        System.out.println(xiaoli.name);
        System.out.println(xiaoli.age);


        int a =5,d = 5;
        int b = a++;
        int c = ++d;
        System.out.println(b);
        System.out.println(c);
    }
}
class Person {
    String name;
    String sex;
    int age;

    public Person() {
    }

    public Person(String name) {

        this.name = name;
    }

    public Person(String  name,String sex) {

        this.name = name;
        this.sex = sex;
    }
    public Person(String name,String sex,int age) {

        this.name = name;
        this.sex = sex;
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
}

