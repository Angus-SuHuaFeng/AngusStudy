package HomeWork.BankTest;

import java.util.Scanner;

public class BankThread implements Runnable{
    BankAccount bankAccount = null;
    int money;
    int flag;

    public BankThread(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void run() {
        synchronized (bankAccount){
            System.out.println("存钱请输入1     取钱请输入2     退出系统请输入3");
            Scanner scanner = new Scanner(System.in);
            flag = scanner.nextInt();
            if (flag>=3||flag<1){
                return;
            }
            System.out.println("请输入"+(flag==1?"存入":"取出")+"金额:");
            money = scanner.nextInt();
            if (flag==1){
                bankAccount.increase(money);
            }else if(flag==2) {
                bankAccount.decrease(money);
            }
        }
    }
}
