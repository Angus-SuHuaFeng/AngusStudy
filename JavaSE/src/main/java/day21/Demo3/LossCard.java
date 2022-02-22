package day21.Demo3;

public class LossCard extends BankBusinessTemplate {
    @Override
    protected String Business() {
        System.out.println("挂失补卡");
        return "四星好评";
    }
}
