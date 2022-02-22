package day22;

/**
 * @author 86155
 */
public class Test {
    public static void f1(Integer i){
        System.out.println("包装类型"+i);
    }
    public static void f2(int i){
        System.out.println("基本数据类型"+i);
    }

    public static void main(String[] args) {
        /**
         * 自动装箱:
         */
        //这个就是自动装箱的例子，调用了类方法Integer.valueOf(int)
        Integer i1 =1;
        System.out.println(i1);
        //这里存在自动拆箱，调用了实例方法intValue()
        int i2 = i1;
        System.out.println(i2);
        //这里返回的是true，因为自动拆箱了
        System.out.println(i1==i2);

        Integer ten = Integer.valueOf(10);
        //自动装箱拆箱
        f1(4);
        f2(ten);

        System.out.println("--------------------------------");
        Integer a = Integer.valueOf(10);
        Integer b = Integer.valueOf(10);
        //equals()方法自动重写了
        System.out.println(a.equals(b));

        System.out.println("--------------------------------");
        //当后面的值在-128~127之间，会从缓存区中拿相应的对象，前提是有相同值的对象,类似静态变量,值一样会存到相同的地址

        Integer a1 = 10;
        Integer a2 = 10;
        System.out.println(a1.equals(a2));


    }
}
