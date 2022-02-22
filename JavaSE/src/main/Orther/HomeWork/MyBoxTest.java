package HomeWork;

public class MyBoxTest {
    public static void main(String[] args) {
        MyBox<Integer> m1 = new MyBox<>();
        m1.setContext(123);
        System.out.println(m1.getContext());
        MyBox<String> m2 = new MyBox<>();
        m2.setContext("daw");
        System.out.println(m2.getContext());
    }
}
class MyBox<T>{
    private T context;

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        this.context = context;
    }
}
