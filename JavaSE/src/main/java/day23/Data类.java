package day23;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Angus
 */
public class Data类 {
    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        //返回从1970-01-01到现在的毫秒数
        System.out.println(date.getTime());
        System.out.println(date);
        //使用setTime方法修改时间
        date.setTime(date.getTime()+2*60*60*1000);
        System.out.println(date);

        //使用SimpleDateFormat方法格式化时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        System.out.println(format);

        //恢复默认格式,这里会有一个异常
        Date parse = simpleDateFormat.parse(format);
        System.out.println(parse);
    }
}
