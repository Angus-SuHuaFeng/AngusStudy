package day21.Demo1;

/**单例模式:只允许创建一个对象
 * static：1.对象和类一起加载
 *         2.只加载一次
 *         3.static共用一个地址，静态的都共用一个
 *  软件运行中会创建很多对象，而创建对象的过程会消耗大量内存空间
 *  使用单例模式可以减少内存使用量
 */

//单例模式
class SingletonDemo {
    private static SingletonDemo instance = new SingletonDemo();
    private SingletonDemo() {}
        public static SingletonDemo getInstance(){
            return instance;
        }
}

public class Test{
    public static void main(String[] args) {
        //SingletonDemo s1 = new SingletonDemo();
        //SingletonDemo s2 = new SingletonDemo();
        SingletonDemo s1 = SingletonDemo.getInstance();
        SingletonDemo s2 = SingletonDemo.getInstance();
        /**
         * 因为getInstance方法被static修饰,之前学的static共用一个地址
         * 所以这里s1,s2对象相当于1个对象,所以，实现了单例模式只允许创建一个对象
         */
    }
}
