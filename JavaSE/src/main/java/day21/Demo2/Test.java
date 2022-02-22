package day21.Demo2;


/**
 * 代理模式：可以在不改变源码（基本不改变）的情况下，对目标对象进行访问，控制，功能拓展
 * 1.目标对象：实际完成功能的对象                 技术工人（realworker）
 * 2.代理对象：是目标对象的替身                      经理（proxy）
 * 3.客户：                                       你自己（...）
 *
 * 以4s店保养汽车为例
 */
public class Test {
    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.work();
    }
}
