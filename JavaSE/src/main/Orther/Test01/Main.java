package Test01;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入年份:");
        int year,month;
        year = sc.nextInt();
        System.out.print("请输入月份:");
        month = sc.nextInt();
        if(month==0||month>12){
            System.out.print("月份输入错误,请重新输入:");
            month = sc.nextInt();
        }
        sc.close();
        DateCalender date = new DateCalender(year,month);
        System.out.println("			"+year+"年"+month+"月");
        System.out.println("-----------------------------------------------------------");
        System.out.println("日"+"\t"+"一"+"\t"+"二"+"\t"+"三"+"\t"+"四"+"\t"+"五"+"\t"+"六");
        date.print();
    }
}


//自定义日期类
class DateCalender{
    int year;
    int month;
    int FirstdayOfMonth=getFirstdayOfMonth(year, month); //某年某月第一天星期几
    int day;

    public DateCalender(){

    }
    public DateCalender(int year){
        this.year = year;
    }
    public DateCalender(int year, int month) {
        super();
        this.year = year;
        this.month = month;
    }
    public DateCalender(int year, int month, int day) {
        super();
        this.year = year;
        this.month = month;
        this.day = day;
    }

    //求某年某月有几天
    public static int DayCount(int year,int month){
        int cout;
        if(month==2){
//			if(YearJardge(year)){
//				return 29;
//			}else {
//				return 28;
//			}
            cout = YearJardge(year)?29:28;
        }else if(month==4||month==6||month==9||month==11){
            cout = 30;
        }else {
            cout = 31;
        }

        return cout;
    }
    //求平年闰年，true为闰年，false为平年
    public static boolean YearJardge(int year){
        if(year%100==0){
            if(year%400==0){
                return true;
            }else {
                return false;
            }
        }else{
            if(year%4==0){
                return true;
            }else {
                return false;
            }
        }
    }
    //求某年某月第一天是星期几
    public int getFirstdayOfMonth(int y,int m){
        int c,w;
        int d=1;
        if(m<=2)
        {
            m+=12;
            y=y-1;
        }
        c=y/100;
        y=y%100;/*新历法公式推导，直接忽略中间空缺的15天*/
        w=c/4-2*c+y+y/4+13*(m+1)/5+d-1;/*新历法，1582，规定10月4号的下一天为10月15号*/
        w%=7;
        if(w<=0) {
            w+=7;
        }
        return w;
    }

    public void print(){
        int n = FirstdayOfMonth;
        for(int i=0;i<n;i++){
            System.out.print("	");
        }
        for(int i=1;i<=DayCount(year,month);i++){
            System.out.print(i+"\t");
            if((i+n)%7==0){
                System.out.println();
            }
        }
    }
}
