package day23;

/**
 * StringBuffer和StringBuilder可以多次修改而不产生新的对象
 * 频繁使用字符串拼接的时候会创建大量的对象
 * 所以这时要用到StringBuffer和StringBuilder
 *
 * StringBuffer线程安全，但是速度慢
 * StringBuilder线程不安全，但是速度快
 * @author Angus
 */
public class String类 {
    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        //append方法将参数（字符或字符串）拼接到内部的字符数组中，并返回this，因此可以进行后续的拼接,追加！
        stringBuffer.append('a');
        System.out.println(stringBuffer);
        System.out.println(stringBuffer.append("asdfgfdsa"));
        //insert方法，在索引位置 插入！
        System.out.println(stringBuffer.insert(4,"hello word"));
        //delete方法，删除指定区间的字符左闭右开
        System.out.println(stringBuffer.delete(1,5));
    }
}
