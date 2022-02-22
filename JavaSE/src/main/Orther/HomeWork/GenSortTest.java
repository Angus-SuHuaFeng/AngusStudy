package HomeWork;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//public class GenSortTest {
//    public static void main(String[] args) {
//        ArrayList<Integer> integers = new ArrayList<>();
//        integers.add(1);
//        integers.add(2);
//        integers.add(7);
//        List<Integer> integers1 = mySort(integers);
//        Print(integers1);
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("dwa");
//        strings.add("Dwa");
//        strings.add("Adwaa");
//        strings.add("AAAAdwadawfwae");
//        strings.add("a");
//        List<String> strings1 = mySort(strings);
//        Print(strings1);
//    }
//    public static <T> List<T> mySort(List<T> t){
//        int size = t.size();
//        t.sort(new Comparator<T>() {
//            @Override
//            public int compare(T o1, T o2) {
//                if (o1 instanceof String){
//                    return ((String) o1).compareTo((String) o2);
//                }
//                else {
//                    return (int)o1>(int)o2?1:0;
//                }
//            }
//        });
//        List<T> list = new ArrayList<>();
//        list.add(t.get(0));
//        list.add(t.get(size-1));
//        System.out.println(t);
//        return list;
//    }
//    public static  void Print(List t){
//        System.out.println(t);
//        System.out.println("min:"+t.get(0)+"\t"+"max:"+t.get(t.size()-1));
//    }
//}
