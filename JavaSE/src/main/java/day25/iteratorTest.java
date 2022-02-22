package day25;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Angus
 */
public class iteratorTest {
    public static void main(String[] args) {
        List<String> list3 = new ArrayList<>();
        list3.add("ZQQ");
        list3.add("SHF");
        list3.add("SYN");
        /*
            iterator: 迭代器
            集合中的iterator()方法:   返回该列表中的元素的迭代器。
                   ZQQ      SHF      SYN
                ^
            iterator  迭代器指向第一个元素之前的一个位置
         */
        Iterator<String> iterator = list3.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        //现在多用foreach,(iter)
        for (String s : list3) {
            System.out.println(s);
        }

        //用超类Object可以添加多个数据类型的元素
        List<Object> list4 = new ArrayList<>();
        list4.add("1");
        list4.add(new Integer("2"));
        list4.add(new Long(3));
        for (Object o : list4) {
            System.out.println(o);
        }

    }
}
