package day14;

public class 数据封装 {
    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.setName("小明");
        System.out.println(animal.getName());
        /**
         * * 将类名作为方法参数
         */
        Introduce(animal);
    }
    //将Animal类作为参数
    public static void Introduce(Animal animal){
        System.out.println(animal.getName());
    }
}

/**
 * 数据封装：保护类里的字段，比如name,age,color
 * 类里设置一系列的方法，可以使外部对对象的字段操作，而且是只允许外部通过封装好的方法来操作
 */
class Animal{
    private String name;
    private int age;
    private String color;

    //比如可以设置方法来操作字段
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}


