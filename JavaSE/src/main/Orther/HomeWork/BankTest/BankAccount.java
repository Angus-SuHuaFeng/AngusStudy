package HomeWork.BankTest;

class BankAccount {
    String BankAccount;
    int Balance;

    public synchronized void increase(int money){
        Balance+=money;
        System.out.println("您的余额为:"+Balance);
    }

    public synchronized void decrease(int money){
        if (Balance<money){
            System.out.println("余额不足！");
        }else {
            Balance-=money;
            System.out.println("您的余额为:"+Balance);
        }
    }

    public BankAccount() {
    }

    public BankAccount(String bankAccount, int balance) {
        BankAccount = bankAccount;
        Balance = balance;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "BankAccount='" + BankAccount + '\'' +
                ", Balance=" + Balance +
                '}';
    }
}
