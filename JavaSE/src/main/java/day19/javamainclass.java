package day19;
//java核心类

/** String类方法：
 * 1.toUpperCase()方法：s1.toUpperCase()   s1为String类型
 *    返回值
 *    一个新的字符串，在其中 stringObject 的所有小写字符全部被转换为了大写字符。
 *    toLowerCase()方法
 *    替换为小写
 *  2.equals()方法： s1.equals(s2)   s1,s2均为String类型
 *    当我们想要比较两个字符串是否相同时，要特别注意，我们实际上是想比较字符串的内容是否相同。
 *    必须使用equals()方法而不能用==,要忽略大小写比较，使用equalsIgnoreCase()方法
 *  3.String类还提供了多种方法来搜索子串、提取子串。
 *    1.contains()方法:  "Hello".contains("ll"); // true
 *    判断是否包含子串,参数是CharSequence而不是String，因为CharSequence是String的父类。
 *    2.搜索子串的更多的例子：
 *      "Hello".indexOf("l"); // 2
 *      "Hello".lastIndexOf("l"); // 3
 *      "Hello".startsWith("He"); // true
 *      "Hello".endsWith("lo"); // true
 *    3.提取子串的例子：  索引号是从0开始的。
 *      "Hello".substring(2); // "llo"
 *      "Hello".substring(2, 4); "ll"
 *  4.去除首尾空白字符:
 *    1.trim()方法
 *      移除字符串首尾空白字符。空白字符包括空格，\t，\r，\n
 *    2.strip()方法
 *      它和trim()不同的是，类似中文的空格字符\u3000也会被移除
 *  5.isEmpty()和isBlank()来判断字符串是否为空和空白字符串
 *      "".isEmpty(); // true，因为字符串长度为0
 *      "  ".isEmpty(); // false，因为字符串长度不为0
 *      "  \n".isBlank(); // true，因为只包含空白字符
 *      " Hello ".isBlank(); // false，因为包含非空白字符
 */
public class javamainclass {
    public static void main(String[] args) {
        String s1 = "Hello";
        String s2 = "hello";
        String s3 = "Hadoop";
        String s;
        //1.大小写转换;
        s = s1.toUpperCase();  //不改变本身，就像c语言里调用函数不改变参数
        System.out.println(s);  //输出大写
        s = s1.toLowerCase();
        System.out.println(s);  //输出小写
        //2.比较函数
        System.out.println(s2.equals(s)); //true
        //3.分割字符串,split()
        String s4 = "A,B,C,D";
        String[] ss = s.split("\\,"); // {"A", "B", "C", "D"}
        //4.拼接字符串，join()
        String[] arr = {"A", "B", "C"};
        String a = String.join("***", arr); // "A***B***C"
        /**
         * 类型转换
         */
        //1.静态方法valueOf(),把任意基本类型或引用类型转换为字符串:这是一个重载方法，编译器会根据参数自动选择合适的方法
        String.valueOf(123); // "123"
        String.valueOf(45.67); // "45.67"
        String.valueOf(true); // "true"
        String.valueOf(new Object()); // 类似java.lang.Object@636be97c
        //2.要把字符串转换为其他类型，就需要根据情况。例如，把字符串转换为int类型
        int n1 = Integer.parseInt("123"); // 123
        int n2 = Integer.parseInt("ff", 16); // 按十六进制转换，255
        //把字符串转换为boolean类型：
        boolean b1 = Boolean.parseBoolean("true"); // true
        boolean b2 = Boolean.parseBoolean("FALSE"); // false
        /**
         * 转换为char[]
         */
        //String和char[]类型可以互相转换，如果修改了char[]数组，String并不会改变
        //这是因为通过new String(char[])创建新的String实例时，它并不会直接引用传入的char[]数组,
        //而是会复制一份，所以，修改外部的char[]数组不会影响String实例内部的char[]数组，因为这是两个不同的数组。
        //从String的不变性设计可以看出，如果传入的对象有可能改变，我们需要复制而不是直接引用。
        char[] cs = "Hello".toCharArray(); // String -> char[]
        String e = new String(cs); // char[] -> String
        System.out.println(e);//Hello
        cs[0] = 'X';
        System.out.println(e);//Hello


    }
}
