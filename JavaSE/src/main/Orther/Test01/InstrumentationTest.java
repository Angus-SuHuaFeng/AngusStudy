package Test01;

public class InstrumentationTest {
    public static void main(String[] args) {
        Person s = new Person(1, "s", 21);
        System.out.println(ObjectShallowSize.sizeOf(s));
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
}
