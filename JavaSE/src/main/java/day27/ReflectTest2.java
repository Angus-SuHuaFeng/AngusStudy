package day27;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Angus
 *          反射的应用
 *              （动态）获取某一个对象中的某一个属性
 *              获取某一个对象中的某一个方法并调用
 *              获取某一个类的构造器创建对象
 *
 */
public class ReflectTest2 {
    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Student st1 = new Student("小明",20);
        Student qq = new Student("倩倩", 19);
        //获取Student的类对象
        Class aClass = Student.class;
        //获取Student类中的name属性
        Field nameField = aClass.getDeclaredField("name");
        //设置Student中的name属性的访问权限（name属性本来是private修饰的外部无法访问），使该属性可以被访问
        nameField.setAccessible(true);
        //获取st1的name属性
        String o = String.valueOf(nameField.get(st1));
        System.out.println(o);
        //获取Student类的setName方法
        Method setNameMeyhod = aClass.getDeclaredMethod("setName", String.class);
        //在st1对象上调用setName方法
        setNameMeyhod.invoke(st1,"宝贝");

        //获取Student类的getName()方法
        Method getnameMethod = aClass.getDeclaredMethod("getName");
        //在st1对象上调用hetName方法
        String invoke = String.valueOf(getnameMethod.invoke(st1));
        System.out.println(invoke);

        //获取构造器(默认调用无参构造器)
        Student o1 = (Student)aClass.newInstance();
        System.out.println(o1.toString());
        //调用无参构造器
        Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class, int.class);
        Student zqq = (Student) declaredConstructor.newInstance("ZQQ", 20);
        System.out.println(zqq.toString());


    }
}


