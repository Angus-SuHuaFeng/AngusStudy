package day14;

/**
 * 继承最基本的作用:代码复用
 * 继承最基本的功能:方法可以重写（覆写）
 */
public class Override01 {
    public static void main(String[] args) {
        Person01 p =new Student01();//向上转型（student01是Person01的子类）
        p.run();

        po q =new po("");
        q.Setname("苏");
        System.out.println(q.Hello());
    }
}

/**
 * 方法重写(快捷键ctrl+o)
 * 对于一个类的实例字段，同样可以用final修饰。用final修饰的字段在初始化后不能被修改
 * final修饰符有多种作用：
 * final修饰的方法可以阻止被覆写；
 * final修饰的class可以阻止被继承；
 * final修饰的field必须在创建对象时初始化，随后不可修改。
 */
class Person01{
    public void run(){
        System.out.println("Person.run");
    }
}

class Student01 extends Person01{
    @Override
    public void run() {
    System.out.println("Student.run");
    }
}
class Teacher extends Student01{
    @Override
    public void run() {
        System.out.println("Teacher.run");
    }

}
//在子类的覆写方法中，如果要调用父类的被覆写的方法，可以通过super来调用

/**
 * 如果父类没有默认的构造方法，子类就必须显式调用super()并给出参数以便让编译器定位到父类的一个合适的构造方法。
 * 子类不会继承任何父类的构造方法。子类默认的构造方法是编译器自动生成的，不是继承的。
 *
 * 继承与覆写的关系：只有继承后才有覆写一说
 */
class ps{
    public ps(){

    }
    public ps(String name) {
        this.name = name;
    }
    protected String name;
    public void Setname(String name){
        this.name=name;
    }
    public  String Hello(){
        return "hello,"+name;
    }
}

 class po extends ps{
    public po(){

    }
     public po(String name) {
         super(name);
     }

     @Override
     public String Hello() {
         return super.Hello()+"!";//super调用
     }
 }