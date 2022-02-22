package day21.Demo3;

public class NewCard extends BankBusinessTemplate {
    @Override
    protected String Business() {
        System.out.println("开卡");
        return "五星好评";
    }
}
