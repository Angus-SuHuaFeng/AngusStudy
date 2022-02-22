package day21.Demo4;

public class NewCard implements BankBusiness {
    @Override
    public String DoBusiness() {
        System.out.println("银行开户");
        return "五星好评";
    }

}
