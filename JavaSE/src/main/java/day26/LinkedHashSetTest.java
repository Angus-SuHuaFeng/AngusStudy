package day26;

import java.util.LinkedHashSet;

/**
 * @author Angus
 */
public class LinkedHashSetTest {
    public static void main(String[] args) {
        /**
         *  LinkedHashSet是HashSet的子类，并且其操作和HashSet相同。
         *  LinkedHashSet不允许重复的元素，但是保留插入元素的顺序。
         *  当对LinkedHashSet进行迭代时，它会按照元素的添加顺序来返回。
         */

        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("zhangsan");
        set.add("lisi");
        set.add("wangwu");

        for (String s : set) {
            System.out.println(s);
            }
        }

}