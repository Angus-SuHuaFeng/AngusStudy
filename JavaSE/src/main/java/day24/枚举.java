package day24;

/**
 * @author 86155
 */

public class 枚举 {
    public static void main(String[] args) {
        //Day是一个类型，枚举类型，引用类型
        //enum里的变量是天然的static final修饰的变量
        //声明一个Day类型变量friday，将枚举值Day.Friday赋值给friday
        Day friday = Day.FRIDAY;

        switch (friday){
            case FRIDAY:
                System.out.println("今天是星期五");
                break;
            case MONDAY:
                System.out.println("今天是星期一");
                break;
            case TUESDAY:
                System.out.println("今天是星期二");
                break;
            case WEDNESDAY:
                System.out.println("今天是星期三");
                break;
            case THURSDAY:
                System.out.println("今天是星期四");
                break;
            case SATURDAY:
                System.out.println("今天是星期六");
            case SUNDAY:
                System.out.println("今天是星期日");
                break;
        }
        //使用enum关键字声明一个枚举类，编译器在创建枚举类的时候会自动添加一些特殊的方法
        //每一个枚举类都有一个静态的values方法，是编译器自己加的
        //values方法返回的是一个包含所有的枚举值的数组
        Day[] arr = Day.values();
        for (Day item :arr){
            System.out.println(item);
        }
        //valueOf方法后面参数只能是枚举类中的值
        Day first = Day.valueOf("FRIDAY");
        //.name可以获取枚举值
        System.out.println(first.name());
        //获取枚举值的序号
        System.out.println(Day.MONDAY.ordinal());

        //枚举的比较
        Day monday = Day.MONDAY;
        Day monday1 = Day.MONDAY;
        //两个都是true
        System.out.println(monday==monday1);
        System.out.println(monday.equals(monday1));
    }
}
