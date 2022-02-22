package day23;

/**
 * @author Angus
 */
public class String方法补充 {
    public static void main(String[] args) {
        String s = "hello";
        String s2 = "world";
        //每次使用String都会创建一个对象，占用一定内存空间，并且String对象不可改变
        String s3 = s +"world!";

        //将字符串转换为字符数组（复习）
        char[] chars = s.toCharArray();
        //将字符串拼接到一起（复习）
        System.out.println(s.concat(s2));
        //从索引处查找（复习）
        System.out.println(s.charAt(2));
        //输出字符串长度（复习）
        System.out.println(s.length());
        //将字符数组转换为字符串（复习）
        char[] chars1 = {'h','e','l','l','o'};
        String s4 = new String(chars1);
        System.out.println(s4);

        /**
         * String方法补充
         */
        //判断字符串中是否含有指定字符串
        System.out.println(s3.contains("ll"));
        //判断字符串是否以指定字符串开头
            System.out.println(s3.startsWith("hell"));
            //从指定索引处开始判断
            System.out.println(s3.startsWith("ell",1));
        //判断字符串是否以指定字符串结尾
        System.out.println(s3.endsWith("ord"));

        //判断字符串是否包含指定字符，包含则返回其第一次出现的索引，不包含则返回-1
            String s5 = "qwterty";
            System.out.println(s5.indexOf('r'));
            //从指定索引处开始判断
            System.out.println(s5.indexOf('t',3));
            //还可以输入数字（Unicode码）   a-97,b-98,c-99
            System.out.println(s5.indexOf(98));
            System.out.println(s5.indexOf(97,3));
        //返回字符（字符串）最后一次出现的索引位置,也同样有很多构造方法
        System.out.println(s5.lastIndexOf('t'));

        //字符串比较
        //1.不忽略大小写
        System.out.println("abc".equals("ABC"));
        //2.忽略大小写
        System.out.println("abc".equalsIgnoreCase("ABC"));

    }
}
