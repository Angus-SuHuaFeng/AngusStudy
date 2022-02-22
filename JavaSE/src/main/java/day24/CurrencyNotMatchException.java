package day24;

/** 自定义异常：自己写一个异常，继承Exception
 * @author 86155
 */
public class CurrencyNotMatchException extends Exception{
    public CurrencyNotMatchException(String massages){
        super(massages);
    }
}
