package day23;

import sun.util.resources.cldr.CalendarData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @author Angus
 */
public class Calendar类 {
    public static void main(String[] args) throws ParseException {
        Calendar data = Calendar.getInstance();
        System.out.println(data.getTime());
        //Calender的优点是方便对每一项进行修改
        data.add(Calendar.YEAR,1);
        System.out.println(data.getTime());
        data.add(Calendar.MONTH,1);
        System.out.println(data.getTime());
        data.add(Calendar.DATE,1);
        System.out.println(data.getTime());
        //单独获得年，月，日
        System.out.println(data.get(Calendar.YEAR));
        System.out.println(data.get(Calendar.MONTH));
        System.out.println(data.get(Calendar.DATE));

        //Calender的格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format2 = simpleDateFormat.format(data.getTime());
        System.out.println(format2);

        //还原格式
        Date parse = simpleDateFormat.parse(format2);
        System.out.println(parse);


    }
}
