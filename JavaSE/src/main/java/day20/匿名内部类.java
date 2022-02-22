package day20;

/**
 * driverCar需要一个Car对象，我们通过接口创建一个匿名的类对象实现这个Car对象
 * 匿名对象没有构造方法，唯一一个没有构造方法的内部类，匿名内部类和局部内部类只能访问外部类的final变量
 */
public class 匿名内部类 {
    public static void main(String[] args) {
        driveCar(new Car() {
            @Override
            public void drive() {
                System.out.println("驾驶BMW汽车");
            }
        });
    }

    public static void driveCar(Car car){
        car.drive();
    }
}

interface Car{
    void drive();
}

