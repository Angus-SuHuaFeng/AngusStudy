package day14;

import java.util.Scanner;
//一个类里还可以使用多个构造器，例如下方，构造器可以相互调用，用this（...）来调用。
//class day14.Person {
//    private String name;
//    private int age;
//
//    public day14.Person(String name, int age) {
//        this.name = name;
//        this.age = age;
//    }
//
//    public day14.Person(String name) {
//        this.name = name;
//        this.age = 12;
//    }
//
//    public day14.Person() {
//    }
//}
public class method {
    public static void main(String[] args) {
//        用含参构造器创建实例,通过含参构造器可以对private成员变量赋值，但获取仍然需要通过函数
        Person li = new Person("li",19);//这样就在创建的时候完成了初始化（赋值）
        System.out.println(li.getName()+li.getAge());
        Person2 song = new Person2("song",17);
        Person2 su =new Person2();//调用定义的无参构造器，无参构造器又调用其他构造器。
        System.out.println(su.name+su.age);
        System.out.println(song.name+song.age+"岁了");
        Person ming = new Person();
//        ming.name = "Xiao Ming"; // 对字段name赋值
//        ming.age = 12; // 对字段age赋值
        /**
         * 通过函数对私有成员变量赋值
         */
        int a;
        Scanner s = new Scanner(System.in);     //输入对象的age
        a = s.nextInt();
        Person xiao = new Person();
        xiao.setName("小明");
        xiao.setAge(a);
        //通过函数getname访问私有成员的值
        System.out.println(xiao.getName()+xiao.getAge()+"岁了");
        Person huang = new Person();
        huang.setnameandage("小黄",10);
        System.out.println(huang.getName()+huang.getAge()+"岁了");


        /**
         * 参数绑定
         * 1.基本数据类型绑定
         * 2.引用数据类型绑定
         */

        //1.基本数据类型绑定
        Person p = new Person();
        int n = 15; // n的值为15
        p.setAge(n); // 传入n的值
        System.out.println(p.getAge()); // 15
        n = 20; // n的值改为20
        System.out.println(p.getAge()); // 15还是20?
        //结果为15，基本类型参数的传递，是调用方值的复制。双方各自的后续修改，互不影响。

        //2.引用数据类型绑定
        Person1 jie = new Person1();
        String[] po = new String[] {"Homer", "Simpson"};
        jie.setName(po);
        System.out.println(jie.getName());

        //改变po[0],po数组的第一个元素修改为"Bart"，类指向的元素改变了
        po[0]="Bart";
        System.out.println(jie.getName());// "Homer Simpson"还是"Bart Simpson"?
        //结果是Bart Simpson
        /**
         * setName()的参数现在是一个数组。一开始，把po数组传进去
         * 然后，修改po数组的内容，结果发现，实例jie的字段jie.name也被修改了！
         * 结论：引用类型参数的传递，调用方的变量，和接收方的参数变量，指向的是同一个对象。
         * 双方任意一方对这个对象的修改，都会影响对方（因为指向同一个对象嘛）。
         */

        Person so = new Person();
        String l = "Bob";
        so.setnameandage(l,10);
        System.out.println(so.getName());//输出为Bob
        //现在改变l的值
        l="Alice";//此处并没有更改原来指向，只是新开内存和指向。
        System.out.println(so.getName());//结果是"Bob"还是"Alice"?
        //结果俩次都是Bob
        /**
         * 字符串也是引用参数，为什么类读出数据不变？因为重写了整个字符串（新开内存和指向，之前的Bob还存在，指向没有变），
         * 类依然指向之前内存块，类读出数据不变，和之前的15，15的结果相同 ,一个道理
         */


        /**
         * 小结：
         * 1、整数、浮点数、字符是基本类型。
         * 2、字符串、数组是引用类型（内存数据的索引）
         *
         * 3、基本类型参数的传递，是调用方值的复制。双方各自的后续修改，互不影响。
         * 4、引用类型参数的传递，调用方的变量和接收方的参数变量，指向的是同一个对象。双方任意一方对这个对象的修改，都会影响对方。
         *
         * 那么3个例子中，
         * 1、整数的参数传递理解了，复制出来的，分家了，自己管理自己的，类读出数据不变。
         * 2、字符串数组的参数传递也理解了，都是指向同一个地方，数组的一个元素改了，类读出数据也就变了（类一直指向这里）。
         * 3、字符串也是引用参数，为什么类读出数据不变？因为重写了整个字符串（新开内存和指向，参看字符串更改章节），
         * 类依然指向之前内存块，类读出数据不变，同结论1。如果只是修改字符串内存中某一个字符的值，则同结论2。
         *
         * 简单总结：类对基本类型是复制数据本身，新开内存。对引用类型是复制指向地址，内存数据本身变化了，
         * 类读出数据跟着变化。但字符串修改，是新开内存新指向，已经不能影响类数据。
         */


    Student w = new Student("su",12,90);
    System.out.println(w.getName());
    }

}
class Person{
    //构造器
    //1.无参构造器，系统会自动提供，如果想存在一个无参，一个有参，则需要定义出来
    public Person(){

    }
    //含参构造器，可以在创建实例的时候直接赋值个成员变量
    //例如Person xiao = new Persion(xiao,18);
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    }
    /**
     * 加了private修饰的成员变量无法直接赋值，但可以通过函数赋值,使用方法（method）来让外部代码可以间接修改成员变量
     */
    protected String name;
    protected int age;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        /**
         * 还可以使用条件判断赋值是否合理
         */
        if (age<=0) {
            System.out.println("年龄不合法");
        }
        this.age=age;
    }
    public int getAge(){
        return this.age;
    }

    //还可以定义一个函数完成名字，年龄的赋值
    public void setnameandage(String name,int age){
        this.name=name;
        this.age=age;
    }
}

class Person1{
    private String[] name;
    //可变参数,可变参数用  (类型...)定义，可变参数相当于数组类型
    public String getName() {
        return this.name[0] + " " + this.name[1];
    }

    public void setName(String[] name) {
        this.name = name;
    }
}


class Person2{
    String name;
    int age;
    //定义三个构造器，可以相互调用
    public Person2(String name,int age){
        this.name=name;
        this.age=age;
    }
    public Person2(String name) {
        this(name,18);
    }
    public Person2() {
        this("su");
    }
}

/**继承:
 * 继承有个特点，就是子类无法访问父类的private字段或者private方法。例如，Student类就无法访问Person类的name和age字段
 * 这使得继承的作用被削弱了。
 * 为了让子类可以访问父类的字段，我们需要把private改为protected。
 * 用protected修饰的字段可以被子类访问：
 * class Person {
 *     protected String name;
 *     protected int age;
 * }
 * 子类的构造方法可以通过super()调用父类的构造方法；
 * 即子类不会继承任何父类的构造方法(即构造器)。子类默认的构造方法是编译器自动生成的，不是继承的。
 */

class Student extends Person{
    public Student(String name, int age, int score) {
        super(name, age);
        this.score = score;
    }
    private int score;

    public int getScore(){
        return this.score;
    }
    public void setscore(int score){
        this.score=score;
    }
}