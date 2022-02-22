package day18;

/** ------------------------导包-------------------------
 * 在写import的时候，可以使用*，表示把这个包下面的所有class都导入进来（但不包括子包的class）
 * 还有一种import static的语法，它可以导入可以导入一个类的静态字段和静态方法,但是import static很少使用。
 *
 * -------------------------作用域-----------------------------
 * public、protected、private修饰符可以用来限定访问作用域
 * protected作用于继承关系。定义为protected的字段和方法可以被子类访问，以及子类的子类
 * 定义为private的field、method无法被其他类访问
 * 定义为public的class、interface可以被其他任何类访问
 *    1. 一个.java文件只能有一个public类，且这个类的类名必须与文件名保持一致
 *    2. 一个类或者类中变量不用public，protected，private修饰的，则其作用域为整个包。
 *    3. public修饰的可以被任何其他类访问，protected主要是作用于继承关系，子类或子类的子类可以访问。private只能在类中访问
 *    4. 局部变量的定义应遵循最小可用原则
 *    5. final修饰的字段无法修改，修饰的类无法继承，修饰的方法无法重写。
 *    6. 写的时候应该先写public，再写private，因为看的时候会先关注一个类暴露给外部的字段和方法。
 *
 */
public class packageAndzuoyongyu {

}
