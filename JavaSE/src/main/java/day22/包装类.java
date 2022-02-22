package day22;

public class 包装类 {
    public static void main(String[] args) {
        //将基本数据类型转换为一个引用数据类型
        Integer int1 = new Integer(55);
        Integer int2 = new Integer(78);
        Integer integer = new Integer("79");
        Float float1 = new Float("9.9");
        Float float2 = new Float(9.8);

        Character c1 = new Character('L');
        Integer i = null;

        //valueof方法,代替构造器,提供另一种方式来创建包装对象
        System.out.println(Integer.valueOf(6));
        //将radix进制的值转成10进制
        System.out.println(Integer.valueOf("1111",2));

        /**
         *  ***value方法，将包装类转为基本数据类型
         */
        //获取Integer对象
        Integer integer1 =Integer.valueOf("28");
        //转换为基本数据类型
        int i1 = integer1.intValue();
        float float3 = integer1.floatValue();
        System.out.println(i1);
        System.out.println(float3);

        /**
         * parse***方法，类似valueOf方法，但是此方法返回基本数据类型
         */
        int i2 = Integer.parseInt("10");
        int i3 = Integer.parseInt("111", 2);
        System.out.println(i2+","+i3);

        /**
         * 静态的toString方法，可以将基本数据类型转化为字符串
         */
        String s = Integer.toString(9);
        System.out.println(s);
        //此处与上面的相反，是将前面的10进制数转换为后面的2进制数
        String s1 = Integer.toString(11, 2);
        System.out.println(s1);
    }

}
