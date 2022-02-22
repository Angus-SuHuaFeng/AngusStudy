package day20;

public class 静态内部类 {
    public static void main(String[] args) {

    }
}

/**
 *  通过static修饰，不依赖于外部类
 *  静态内部类不能使用外部类的非静态成员和方法
 *
 */
class U{
    static class I{

    }
}
