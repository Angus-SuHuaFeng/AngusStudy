package day06;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class test1{
    public int a = 10;
    ArrayList arrayList = new ArrayList(a);

    public static void main(String[] args) {

        test2 test2 = new test2();
        System.out.println(test2.a);
        System.out.println(test2.arrayList);
        test1 test1 = new test1();
        System.out.println(test1.arrayList.size());
        ArrayList arrayList = new ArrayList(2);
        System.out.println(arrayList.size());
    }
}


class test2 extends test1{

}