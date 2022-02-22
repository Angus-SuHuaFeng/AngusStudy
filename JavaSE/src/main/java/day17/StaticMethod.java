package day17;

/**
 * 静态方法：
 * 因为静态方法属于class而不属于实例，因此，静态方法内部，无法访问this变量，也无法访问实例字段，它只能访问静态字段。
 * 静态方法相当于C语言中的函数，调用静态方法则不需要实例变量，通过类名就可以调用
 */
public class StaticMethod {
    public static void main(String[] args) {
        Person2.setnumber(90);
        System.out.println(Person2.number);
    }
}
class Person2{
    protected static int number;    //静态字段
    public static void setnumber(int Value){
        number = Value;     //只能访问静态字段
    }
}