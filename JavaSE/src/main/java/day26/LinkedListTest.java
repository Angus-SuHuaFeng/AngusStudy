package day26;

import java.util.LinkedList;

/**
 * @author Angus
 */
public class LinkedListTest {
    public static void main(String[] args) {
        /**
         * LinkedList<>()  :
         *  链表
         */

        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");

        list.add("a");
        for (int i = list.size()-1;i>=0;i--){
            System.out.println("size:"+list.size()+"\ti:"+i);
            if ("a".equals(list.get(i))){
                list.remove(i);
                System.out.println("移除后："+list.size()+ "\ti:"+i);
            }
        }

//        for (int i = 0;i<list.size();i++){
//            System.out.println(list.size());
//            if ("a".equals(list.get(i))){
//                list.remove(i);
//                System.out.println("移除后："+list.size());
//            }
//        }
        System.out.println(list);
    }
}
