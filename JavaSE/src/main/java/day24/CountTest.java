package day24;

/**
 * @author 86155
 */
public class CountTest {
    public static void main(String[] args) throws CurrencyNotMatchException {
        Count add1 = add1(new Count(100, "人民币"), new Count(100, "人民币"));
//        Count add2 = add1(new Count(100, "人民币"), new Count(100, "欧元"));
        System.out.println(add1.getAmount()+add1.getCount());

        Count count1 = add2(new Count(100, "人民币"), new Count(100, "欧元"));
        System.out.println(count1.getAmount()+count1.getCount());
    }

    public static Count add1(Count m1,Count m2){
        if(!m1.getAmount().equals(m2.getAmount())){
            throw new RuntimeException("货币类型不匹配");
        }else {
            return new Count(m1.getCount()+m2.getCount(),m1.getAmount());
        }
    }

    public static Count add2(Count m1,Count m2) throws CurrencyNotMatchException {
        if(!m1.getAmount().equals(m2.getAmount())){
            throw new CurrencyNotMatchException("货币类型不匹配");
        }else {
            return new Count(m1.getCount()+m2.getCount(),m1.getAmount());
        }
    }
}
