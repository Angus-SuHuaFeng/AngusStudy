package HomeWork;

import java.util.ArrayList;

public class ArrayListTest {
    public static void main(String[] args) {

    }
    class Person {
        int num;
        String name;

        public Person(int num, String name) {
            this.num = num;
            this.name = name;
        }

        public Person() {
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void showInfo() {
            System.out.println("num:" + num + "\tname:" + name);
        }
    }
}