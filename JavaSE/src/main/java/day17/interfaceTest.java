package day17;

/**
 * 接口interface的特点：
 * 1.一个class可以实现多个interface,但是不能使用extends，而要用implements
 * 2.interface可以继承另一个interface extends
 * 3.interface不能有字段(除非是static,并且final)
 * 4.interface的方法都是抽象方法，但是可以定义default方法
 */

/**
 * default方法和抽象类的普通方法是有所不同的。
 * 因为interface没有字段，default方法无法访问字段，而抽象类的普通方法可以访问实例字段。
 */

//public class interfaceTest {
//    public static void main(String[] args) {
//        people p = new people("小明");
//        p.run();
//    }
//}

//接口
//interface Person{
//    String Getname();
//    default void run(){
//        System.out.println(Getname()+"run");
//    }
//}
//接口继承
//class people implements Person{
//    private String name;
//
//    public people(String name) {
//        this.name = name;
//    }
//    @Override
//    public String Getname() {
//        return this.name;
//    }
//}