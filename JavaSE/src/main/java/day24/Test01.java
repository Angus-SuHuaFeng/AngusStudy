package day24;

/** 异常的顶层类是Throwable,Error,Exception
 * Error在代码中不能处理(不能用try捕捉)，但是Exception可以
 * StackOverFlowError（栈内存溢出异常）   OutOfMemoryError（内存溢出异常）  通常无法捕获
 * 受检查异常：编译不通过，直接爆红
 * 运行时异常：编译通过，但是运行时报异常
 * 一个try可以对应多个catch，可以用于检测代码是哪种异常
 * @author 86155
 */
public class Test01 {
    public static void main(String[] args) {
        method1();
    }
    public static void method1(){
        method2();
    }
    public static void method2(){
        try {
            //这里面写觉得有异常的代码
            String str = null;
            //str的值为null，所以不能执行toString方法，故会报空指针异常
            //发生异常后，异常后面的代码就无法执行，但是throws或try-catch后就可以执行了
            //运行后发现有三个异常，说明异常可以传播给调用含有异常的方法的主体
            //如果try里的代码没有异常，那么正常输出try里的结果
            str.toString();
        }catch (Exception e){
            System.out.println("捕捉到异常");
        }finally {
            //这里的代码无论是否捕获到异常都会运行的代码
            //finally一般情况没啥用，因为都会运行，所以在后面加也可以
            //当出现异常又需要运行一些代码时，就需要finally了
            System.out.println("都会执行的代码");
        }
        System.out.println("空指针异常");
    }


}
