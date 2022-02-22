package day15;

import java.util.Scanner;

/**
 * 多态实例：税收及工资计算
 */
public class Polymorphic {
    public static void main(String[] args) {
        double a,b,c;
        Scanner s =new Scanner(System.in);
        System.out.println("请输入各项收入：");
        System.out.println("普通收入 工资收入 国务院津贴");
        a = s.nextDouble();
        b = s.nextDouble();
        c = s.nextDouble();
        Income[] incomes = new Income[] {   //数组还可以这样用
                new Income(a),           //定义三个不同的变量
                new Salary(b),
                new StateCouncilSpecialAllowance(c)
        };
        System.out.println("您需要交的个人所得税为："+totalTax(incomes));
        System.out.println("您的总收入为："+(a+b+c));





    }
    public static double totalTax(Income[] incomes) {
        double total = 0;
        for (Income income: incomes) {
            total = total + income.getTax();
        }
        return total;
    }
}


class Income{
    protected double income;

    public Income(double income) {
        this.income = income;
    }
    public double getTax() {
        return income * 0.1; // 税率10%
    }
}





class Salary extends Income{
    public Salary(double income) {
        super(income);
    }

    @Override
    public double getTax() {
        if (income<=5000){
            return 0;
        }else {
            return (income-5000)*0.2;
        }
    }
}
class StateCouncilSpecialAllowance extends Income{
    public StateCouncilSpecialAllowance(double income) {
        super(income);
    }
    @Override
    public double getTax() {
        return 0;
    }
}
