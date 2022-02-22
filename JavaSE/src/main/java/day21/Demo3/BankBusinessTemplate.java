package day21.Demo3;

import java.util.Random;

/**
 * 模板模式：1.使用抽象类的模板模式
 *          2.使用接口的模板模式
 * 下面以银行业务为例,抽象类的模板模式
 * 1.排号
 * 2.办理业务
 * 3.评价
 */
public abstract class BankBusinessTemplate {

    //1.取号
    protected int GetNumber(){
        int i = (int) Math.random()*1000;
        System.out.println("取到号码"+i);
        return i;
//        Random random = new Random();
//        int i = random.nextInt(1000);
//        System.out.println("取到号码"+i);
//        return i;
    }

    protected abstract String Business();

    protected void SaveEvaluate(int number,String evaluate){
        System.out.println("号码"+number+"的评价是"+evaluate);
    }

    public void line(){
        int number = GetNumber();
        String evaluate = Business();
        SaveEvaluate(number,evaluate);
    }
}
