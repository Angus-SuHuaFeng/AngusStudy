package day27;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Angus
 *      反射的作用:
 *          加载类并获取String类的类对象
 *          获取实现哪些接口
 *          获取声明的构造器（获取特定参数的构造器）
 *          获取声明的属性（获取某一个属性）
 *          获取声明的方法（获取声明的某一个方法）
 *
 *
 */
public class ReflectTest1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {
        String classname = "java.lang.String";
        //加载类并获取String类的类对象
        Class c = Class.forName(classname);

        //获取父类的对象与类名
        Class superclass = c.getSuperclass();
        System.out.println(superclass.getName());

        //获取实现哪些接口
        Class[] interfaces = c.getInterfaces();
        for (Class o : interfaces) {
            System.out.println(o);
        }

        //获取声明的构造器
        Constructor[] declaredConstructors = c.getDeclaredConstructors();
        for (Constructor o :
                declaredConstructors) {
            System.out.println(o);
        }
        //获取特定参数的构造器
        System.out.println("------------------------------------------------");
        Constructor declaredConstructor = c.getDeclaredConstructor(byte[].class, int.class, int.class);
        System.out.println(declaredConstructor);

        //获取声明的属性
        Field[] declaredFields = c.getDeclaredFields();
        for (Field o :
                declaredFields) {
            System.out.println(o);
        }

        //获取某一个属性
        Field hash = c.getDeclaredField("hash");
        System.out.println(hash);

        //获取声明的方法
        Method[] declaredMethods = c.getDeclaredMethods();
        for (Method o :
                declaredMethods) {
            System.out.println(o);
        }

        //获取某一个方法
        Method indexOf = c.getDeclaredMethod("indexOf", int.class);
        System.out.println(indexOf);

    }
}
