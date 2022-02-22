package day21.Demo4;



public class ChangePhoneNumber implements BankBusiness {

    @Override
    public String DoBusiness() {
        System.out.println("更换手机号码");
        return "四星好评";
    }
}

