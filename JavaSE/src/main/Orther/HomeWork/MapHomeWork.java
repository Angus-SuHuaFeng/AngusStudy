package HomeWork;

import java.util.*;

public class MapHomeWork {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put(1,"张三");
        map.put(2,"李四");
        map.put(3,"王五");
        map.put(4,"赵六");
//        打印
        Set set = map.keySet();
        for (Object object : set) {
            System.out.println(object+"\t"+map.get(object));
        }
//        插入
        map.put(5,"钱七");
//        移除
        map.remove(1);
//        修改
        map.replace(2,"张大");
//        修改后打印
        System.out.println("-------------------------------");
        Set set1 = map.keySet();
        for (Object object : set1) {
            System.out.println(object+"\t"+map.get(object));
        }
    }
}


