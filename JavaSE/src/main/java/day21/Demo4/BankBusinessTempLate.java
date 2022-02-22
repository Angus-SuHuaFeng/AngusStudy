package day21.Demo4;


import java.util.Random;

public class BankBusinessTempLate {
    /**
     * 模板模式：1.使用抽象类的模板模式
     *          2.使用接口的模板模式
     * 下面以银行业务为例,使用接口的模板模式
     * 1.排号
     * 2.办理业务
     * 3.评价
     */

    //1.排号
    protected int getNumber(){
        Random random = new Random();
        int i = random.nextInt(1000);
        System.out.println("取到号码："+i);
        return i;
    }

    //2.办理业务
    public void Buisness(BankBusiness business){
        //取号
        int number = getNumber();
        //办理业务
        String s = business.DoBusiness();
        SaveEvaluate(number,s);
    }

    //3.保存评价
    protected void SaveEvaluate(int number, String evaluate){
        System.out.println("号码"+number+"的评价是："+evaluate);
    }

}
