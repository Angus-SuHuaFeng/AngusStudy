package day29;

public class SingletonDemoTest {
    public static void main(String[] args) {
        SingletonDemo s1 = SingletonDemo.getInstance();
        SingletonDemo s2 = SingletonDemo.getInstance();
//        地址相同
        System.out.println(s1 + " , " + s2);
//        判断结果为true
        System.out.println(s1 == s2);

        Singleton s3 = Singleton.getInstance();
        Singleton s4 = Singleton.getInstance();
        System.out.println(s3 + " , " + s4);
        System.out.println(s3 == s4);
    }
}

/**
 *   饿汉式: 上来就创建一个
 */
class SingletonDemo{
    private final static SingletonDemo instance = new SingletonDemo();

    private SingletonDemo() {
    }
    public static SingletonDemo getInstance(){
        return instance;
    }
}

/**
 *   懒汉式: 如果没有才创建，有的话就不创建
 */
class Singleton{
    private static Singleton instance;

    private Singleton(){

    }
    public static Singleton getInstance(){
//        提高效率，如果对象已经创建过了，就不必进入同步代码块
        if (instance == null){
            synchronized (Singleton.class){
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

//Test
//饿汉式
class Singleton2{
    private static Singleton2 instance = new Singleton2();

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return instance;
    }
}

//懒汉式
class Singleton3{
    private static Singleton3 instance;
    private Singleton3(){

    }
    public static Singleton3 getInstance(){
        if (instance == null){
            synchronized (Singleton3.class){
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}