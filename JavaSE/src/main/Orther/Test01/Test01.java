package Test01;
//多态
public class Test01 {
    public static void main(String[] args) {
        test1[] test = new test1[]{
                new test1(1), new test2(78), new test3(90)
        };
    }
}

class test1{
    public test1(double i) {

    }

    public double test1(double a){
        return a*2;
    }
}
class test2 extends test1{

    public test2(double i) {
        super(i);
    }

    @Override
    public double test1(double a) {
        return a*4;
    }
}
class test3 extends test1{
    public test3(double i) {
        super(i);
    }

    @Override
    public double test1(double a) {
        return a*6;
    }
}