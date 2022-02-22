package day14;

/**方法重载:完成类似的功能
 * 在一个类中，我们可以定义多个方法。
 * 如果有一系列方法，它们的功能都是类似的，只有参数有所不同，那么，可以把这一组方法名做成同名方法。
 * 这种方法名相同，但各自的参数不同，称为方法重载（Overload）。
 * 注意：方法重载的返回值类型通常都是相同的。
 */
public class Overload {
    public static void main(String[] args) {
        String s = "Test string";
        int n1 = s.indexOf('t');
        int n2 = s.indexOf("st");
        int n3 = s.indexOf("st", 4);
        System.out.println(n1);
        System.out.println(n2);
        System.out.println(n3);

    }
}

    class Hello{
//    private String name;
//    private int age;
//    public Hello(String name) {
//        this.name=name;
//    }
    public void hello() {
        System.out.println("Hello world");
    }
    public void hello(String name1) {
        System.out.println("Hello"+ name1 +"!");
    }
    public void hello(String name1,String name2){
        System.out.println("Hello"+name1+name2+"!");
    }
    public void hello(String name1,int age) {
        if(age>=18){
            System.out.println("Hi"+name1+"you are "+age);
        }else {
            System.out.println("Hello"+name1+"you are "+age);
        }
    }
}


/**小结
 * 方法重载是指多个方法的方法名相同，但各自的参数不同；
 *
 * 重载方法应该完成类似的功能，参考String的indexOf()；
 *
 * 重载方法返回值类型应该相同。
 */
