package day22;

public class String类 {
    /**
     *  String可以存储字符串序列，不是基本数据类型，是引用类型
     *  内部通过字符数组来保存字符串的
     *
     */
    public static void main(String[] args) {
        String str1 = "str1";
        //concat()方法用于字符串拼接，将参数拼接到字符串尾部
        String str2 = str1.concat("str2");
        System.out.println(str2);

        String str3 = "value";
        String str4 = "value";
        //jdk1.7以后，字符串常量池，检测有相同的值就会找到之前的地址值复值给”新的对象“，实则共用一个地址，相当于一个对象
        System.out.println(str3==str4);

        //String类常用方法
        //1.charAt() 获取指定索引处的字符,传入索引值,从0开始
        String str5 = "qwertyuio";
        System.out.println(str5.charAt(3));
        //2.length() 返回字符串长度
        System.out.println(str5.length());
        //将字符数组转换为字符串
        char[] chars = {'a','b','c'};
        String str6 = new String(chars);
        System.out.println(str6);
        //将字符串转换为字符数组
        String str7 = "qwertyu";
        char[] chars1 = str7.toCharArray();
        for (char a : chars1) {
            System.out.print(a);
        }


    }


}
