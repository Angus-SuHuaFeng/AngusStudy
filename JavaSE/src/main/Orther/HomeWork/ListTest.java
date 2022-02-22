package HomeWork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        List list = new ArrayList();
        //List list1 = new List();错误
        ArrayList alist = new ArrayList();
        list.add(1);
        list.add("zhangsan");
        list.add(new Integer(2));
        list.add(new Double(3.14));
        list.add("zhangsan");
        System.out.println("====直接输出====");
        System.out.println(list);
        System.out.println("====根据索引输出====");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        System.out.println("====使用迭代器输出====");
        Iterator iterator = list.iterator();
        PrintIter(iterator);
    }
    public static void PrintIter(Iterator iterator) {
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
