package day17;

/**********************静态变量(字段)***************************
 * 实例字段在每个实例中都有自己的一个独立“空间”，但是静态字段只有一个共享“空间”，所有实例都会共享该字段
 * 解释:
 * 所有的静态字段都共享一个“空间”
 * 无论修改哪个实例的静态字段，效果都是一样的，所有实例的静态字段都被修改了，原因是静态字段并不属于实例
 */
//例如以下代码
public class Static {
    public static void main(String[] args) {
        Person1 ming = new Person1("小明",19);
        Person1 hong = new Person1("小红",20);
        //重点展示代码：
        ming.number = 80;
        System.out.println(hong.number +","+ming.number);
        /**
         * 结果为80，80 此处对对象ming的number赋值，但是hong的number也被赋值了
         * 下面对hong赋值
         */
        hong.number = 90;
        System.out.println(hong.number +","+ming.number);
        /**
         * 结果为90，90 再次证明了“所有的静态字段都共享一个“空间”。”
         *虽然实例可以访问静态字段，但是它们指向的其实都是Person1 class的静态字段。所以，所有实例共享一个静态字段。
         *         ┌──────────────────┐
         * ming ──>│Person1 instance  │
         *         ├──────────────────┤
         *         │name = "Xiao Ming"│
         *         │age = 18          │
         *         │number ───────────┼──┐    ┌─────────────┐
         *         └──────────────────┘  │    │Person1 class│
         *                               │    ├─────────────┤
         *                               ├───>│number = 80  │
         *         ┌──────────────────┐  │    └─────────────┘
         * hong ──>│Person1 instance  │  │
         *         ├──────────────────┤  │
         *         │name = "Xiao Hong"│  │
         *         │age = 20          │  │
         *         │number ───────────┼──┘
         *         └──────────────────┘
         */

        /**
         * 因此，不推荐用实例变量.静态字段去访问静态字段，因为在Java程序中，实例对象并没有静态字段。
         * 在代码中，实例对象能访问静态字段只是因为编译器可以根据实例类型自动转换为类名.静态字段来访问静态对象。
         * 推荐用类名来访问静态字段。可以把静态字段理解为描述class本身的字段（非实例字段）。
         * 对于上面的代码，更好的写法是：
         * Person.number = 99;
         * System.out.println(Person.number);
         */

    }
}

class Person1{
    public String name;
    public int age;
    public static int number;

    public Person1(String name,int age){
        this.age = age;
        this.name = name;
    }
}
/**
 * 接口的静态字段
 * 因为interface是一个纯抽象类，所以它不能定义实例字段。
 * 但是，interface是可以有静态字段的，并且静态字段必须为final类型：
 */
interface Person3 {
    public static final int MALE = 1;
    public static final int FEMALE = 2;
}
/**
 * 实际上，因为interface的字段只能是public static final类型，
 * 所以我们可以把这些修饰符都去掉，上述代码可以简写为：
 */
interface Person5 {
    // 编译器会自动加上public statc final:
    int MALE = 1;
    int FEMALE = 2;
}