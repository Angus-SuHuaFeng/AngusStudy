package HomeWork;

import java.util.ArrayList;

public class ListTXL {
    public static void main(String[] args) {
        ArrayList<StudentTXL> txl = new ArrayList<>();
        txl.add(new StudentTXL("15547477278","苏华锋"));
        txl.add(new StudentTXL("15647477278","赵六"));
        txl.add(new StudentTXL("15747477278","张三"));
        txl.add(new StudentTXL("15847477278","李四"));
        txl.add(new StudentTXL("15947477278","王五"));
        System.out.println(txl);
    }
}
class StudentTXL{
    String name;
    String tel;

    public StudentTXL(String tel, String name) {
        this.name = name;
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "name=" + name +" " +
                "tel=" + tel + "\n";
    }

}