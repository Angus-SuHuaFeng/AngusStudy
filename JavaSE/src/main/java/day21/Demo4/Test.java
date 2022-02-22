package day21.Demo4;


public class Test {
    public static void main(String[] args) {
        BankBusinessTempLate tempLate =  new BankBusinessTempLate();
        BankBusiness s2 = new NewCard();
        BankBusiness s3 = new ChangePhoneNumber();
        tempLate.Buisness(s2);
        tempLate.Buisness(s3);
    }


}
