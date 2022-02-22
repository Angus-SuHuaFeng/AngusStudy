package day20;

/**
 * 局部内部类存在于方法中
 * 访问权限限于方法的作用域
 * 不能访问static修饰符
 */
public class 局部内部类 {
    public static void main(String[] args) {

    }
}
class C{
    void my(){
        class D{

        }
    }
}