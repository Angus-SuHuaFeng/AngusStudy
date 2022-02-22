package day20;

public class 成员内部类 {
    public static void main(String[] args) {
        A a = new A();
        a.run();
        a.eat();
        A.B b = a.new B(); //创建内部类对象需要先创建外部类对象，然后再创建内部类对象语法为：
                           //外部类.new 内部类.var
        System.out.println(b.name);
        b.say();
    }
}
class A{
    String name = "小明";
    void run(){
        System.out.println(name+"跑的快");
    }
    B b = new B();
    void eat(){
        System.out.println(b.name);
        b.say();
    }
    //B为内部类

    /**
     * 内部类可以直接访问外部类的字段，而外部类要访问内部类的字段就需要先创建对象，再引用
     */
     class B{
        String name = "小芳";
        void say(){
            System.out.println(name+"说话很好听");
            //如果名字重复，默认是当前类的字段，如果要引用外部类的同名字段，需要外部类.this.变量名（字段）
            System.out.println(A.this.name+"说话也很好听");
        }
    }
}
