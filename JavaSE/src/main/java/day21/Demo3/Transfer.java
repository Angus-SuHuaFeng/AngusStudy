package day21.Demo3;

public class Transfer extends BankBusinessTemplate{
    @Override
    protected String Business() {
        System.out.println("转账");
        return "五星好评";
    }
}
