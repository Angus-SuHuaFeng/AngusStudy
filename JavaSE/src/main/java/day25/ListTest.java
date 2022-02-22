package day25;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Angus
 * 2021.1.17
 */
public class ListTest {
    public static void main(String[] args) {
        /**
         * List接口继承自Collection接口,拥有该 接口中的所有方法
         * List: 有序的列表,保留了元素的顺序添加,允许将元素添加到指定位置（有序列表）
         *       !可以保存重复元素 !
         *
         */


        //创建ArrayList集合
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list.add("ZQQ");
        list.add("SHF");
        list.add("SYN");
        list1.add("ZQQ");
        list1.add("SHF");
        list1.add("SYN");
        //在指定索引位置添加元素
        list.add(0,"JAVA");
        /*遍历List集合
            1.for   不常用，有局限
            2.toString()
            3.foreach()   本质为迭代器遍历
         */
        for (int i = 0; i < list.size(); i++) {
            //get(int x);方法返回指定索引位置的元素
            System.out.println(list.get(i));
        }

        System.out.println(list.toString());
        for (String s : list) {
            System.out.println(s);
        }
        /*
            1.isEmpty()  判空，如为空返回True
            2.contains(Object o) 判断是否包含指定元素，是返回True
            3.containsAll(Collection<?> c) 如果此列表包含指定集合的所有元素，则返回true。
            3.toString()
            4.toArray()  以正确的顺序（从第一个到最后一个元素）返回一个包含此列表中所有元素的数组。(将集合转换成数组，asList()为将数组转换成集合)
            5.indexOf()  返回指定元素第一次出现的索引位置
            6.lastIndexOf()  返回指定元素最后一次出现的索引位置,如果没找到返回-1
            7.set()      替换指定索引位置的元素，返回旧元素
            8.remove(int x)   删除指定索引位置的元素，返回值删除的值，有俩个重载
            9.remove(Object o)  从列表中删除指定元素的第一个出现(如果存在)，返回True，如果此列表不包含该元素，则它将保持不变,返回false
           10.remove(Collection<?> c)   从此列表中删除包含在指定集合中的所有元素(删除c中所有元素)
           11.retainAll(Collection<?> c) 从此列表中删除不包含在指定集合中的所有元素(只保留c中所有元素)
           12.clear()    从此列表中删除所有元素
           13.sort(Comparator<? super E> c)     使用附带的 Comparator排序规则排序此列表来比较元素。 (在ListSort里详细说明)
         */

        System.out.println("isEmpty(): "+ list.isEmpty());
        System.out.println("contains(Object o): "+ list.contains("ZQQ"));
        System.out.println("contains(Collection<?> o: "+ list.containsAll(list1));
        System.out.println("toString(): "+ list.toString());
        System.out.println("indexOf(): "+ list.indexOf("ZQQ"));
        System.out.println("lastIndexOf(): "+ list.lastIndexOf("ZQQ"));
        System.out.println("set(): (此处输出的为旧元素的值)"+ list.set(0,"ZQQ")+" 新元素的值为: "+ list.get(0));
        System.out.println("remove(): "+ list.remove(0));
        System.out.println("remove(Object): "+ list.remove("SHX"));

        //集合转换成数组(toArray()的用法)
        Object[] arr = list.toArray();
        for (Object o : arr) {
            System.out.println(o);
        }
        //将集合塞到空的数组中,str与str1地址相同，本质上为一个数组
        String[] str = new String[list.size()];
        String[] str1 = list.toArray(str);
        for (String s : str1) {
            System.out.println(s);
        }
        System.out.println("=======================================");
        //将数组转换成集合asList()方法的用法
        List<String> str2 = Arrays.asList(str);
        for (String s : str2) {
            System.out.println(s);
        }
    }
}
