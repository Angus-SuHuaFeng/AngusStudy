package Test2;

import java.util.Random;

//定义储蓄卡类，属性账号、余额；功能，开卡、取钱、存钱、查询余额。定义测试类使用存储卡类。
public class BankTest {
    public static void main(String[] args) {
        Card card = new Card();
        Card open = card.Open();
        open.inquire();
        card.setBalance(100);
        card.in(100);
        card.out(20);
        card.inquire();
    }
}
class Card{
    int num;
    double balance;

    public Card() {
    }

    public Card(int num, double balance) {
        this.num = num;
        this.balance = balance;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Card Open(){
        int num;
        Random random = new Random();
        num = random.nextInt(100000000);
        Card card1 = new Card(num,0);
        System.out.println("开卡成功,账号为:"+num);
        return card1;
    }
    public void in(int money){
        this.balance = balance + money;
        System.out.println("存入成功，余额为:"+this.balance);
    }
    public void out(int money){
        this.balance = balance-money;
        System.out.println("取钱成功，余额为:"+this.balance);
    }
    public void inquire(){
        System.out.println("余额为:"+balance);
    }

}