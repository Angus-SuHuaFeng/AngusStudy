package day27;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReflectTest3 {

    public static <T> List<T> queryForList(String Sql,Class<T> c) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<T> ts = new ArrayList<T>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //数据库连接url,用于配置参数:主机名,端口,数据库名称,编码格式
        String url = "jdbc:mysql://localhost:3306/library?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        //用户名
        String username = "root";
        String password = "root";
        Statement statement=null;
        Connection con=null;

        try {
            con = DriverManager.getConnection(url, username, password);
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(Sql);
            while (resultSet.next()){
                //通过反射创建对象
                T obj = c.newInstance();
                //获取结果集的元数据，元数据就是对数据的说明
                ResultSetMetaData metaData = resultSet.getMetaData();
                //获取结果集的数
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    //获取每列的列名
                    String columnName = metaData.getColumnName(i);
                    //获取列值
                    Object columnvalue = resultSet.getObject(columnName);
                    if (columnvalue==null){
                        columnvalue=0;
                    }
                    //获取对应属性
                    Field declaredField = c.getDeclaredField(columnName);
                    //设置访问权限
                    declaredField.setAccessible(true);
                    //将列值放入到对象的属性中
                    declaredField.set(obj,columnvalue);
                }
                ts.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ts;
    }
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        List<Administrator> administrators = queryForList("select * from administrator", Administrator.class);
        for (Administrator o :
                administrators) {
            System.out.println(o.toString());
        }
        System.out.println("******************************************************************");
        List<Book> books = queryForList("select * from book where Area = 'A'", Book.class);
        for (Book o :
                books) {
            System.out.println(o.toString());
        }
        List<BrownHistory> brownHistories = queryForList("select * from brownhistory", BrownHistory.class);
        for (BrownHistory o :
                brownHistories) {
            System.out.println(o.toString());
        }
        System.out.println("*****************************************************************");
        List<User> users = queryForList("select * from user", User.class);
        for (User o:
             users) {
            System.out.println(o.toString());
        }
    }
}

//library数据库中的所有表
class Administrator{
    private String Area;
    private String Name;
    private int Workid;
    private String Gender;
    private long Tel;
    private long Std;
    private String PassWord;
    public Administrator(){

    }

    public Administrator(String area, String name, int workid, String gender, long tel, long std, String passWord) {
        Area = area;
        Name = name;
        Workid = workid;
        Gender = gender;
        Tel = tel;
        Std = std;
        PassWord = passWord;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getWorkid() {
        return Workid;
    }

    public void setWorkid(int workid) {
        Workid = workid;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public long getTel() {
        return Tel;
    }

    public void setTel(long tel) {
        Tel = tel;
    }

    public long getStd() {
        return Std;
    }

    public void setStd(long std) {
        Std = std;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "Area=" + Area +
                ", Name=" + Name +
                ", Workid=" + Workid +
                ", Gender=" + Gender +
                ", Tel=" + Tel +
                ", Std=" + Std +
                ", PassWord=" + PassWord +
                '}';
    }
}

class Book{


    private int Bookid;
    private String Area;
    private String name;
    private String ISBN;
    private String Author;
    private String Publish;
    private String Bookarea;
    private String Have;
    private int Borrowid;

    public Book(int bookid, String area, String name, String ISBN, String author, String publish, String bookarea, String have, int borrowid) {
        Bookid = bookid;
        Area = area;
        this.name = name;
        this.ISBN = ISBN;
        Author = author;
        Publish = publish;
        Bookarea = bookarea;
        Have = have;
        Borrowid = borrowid;
    }
    public Book(){

    }

    public int getBookid() {
        return Bookid;
    }

    public void setBookid(int bookid) {
        Bookid = bookid;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublish() {
        return Publish;
    }

    public void setPublish(String publish) {
        Publish = publish;
    }

    public String getBookarea() {
        return Bookarea;
    }

    public void setBookarea(String bookarea) {
        Bookarea = bookarea;
    }

    public String getHave() {
        return Have;
    }

    public void setHave(String have) {
        Have = have;
    }

    public int getBorrowid() {
        return Borrowid;
    }

    public void setBorrowid(int borrowid) {
        Borrowid = borrowid;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Bookid=" + Bookid +
                ", Area='" + Area + '\'' +
                ", name='" + name + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", Author='" + Author + '\'' +
                ", Publish='" + Publish + '\'' +
                ", Bookarea='" + Bookarea + '\'' +
                ", Have='" + Have + '\'' +
                ", Borrowid='" + Borrowid + '\'' +
                '}';
    }
}
class BrownHistory{
    private String Brownid;
    private String Num;
    private Timestamp BrownTime;
    private int Bookid;
    private String Return;
    private String Overdue;
    private int BrownDay;

    public BrownHistory(String brownid, String num, Timestamp brownTime, int bookid, String aReturn, String overdue, int brownDay) {
        Brownid = brownid;
        Num = num;
        BrownTime = brownTime;
        Bookid = bookid;
        Return = aReturn;
        Overdue = overdue;
        BrownDay = brownDay;
    }
    public BrownHistory (){

    }

    public String getBrownid() {
        return Brownid;
    }

    public void setBrownid(String brownid) {
        Brownid = brownid;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public Timestamp getBrownTime() {
        return BrownTime;
    }

    public void setBrownTime(Timestamp brownTime) {
        BrownTime = brownTime;
    }

    public int getBookid() {
        return Bookid;
    }

    public void setBookid(int bookid) {
        Bookid = bookid;
    }

    public String getReturn() {
        return Return;
    }

    public void setReturn(String aReturn) {
        Return = aReturn;
    }

    public String getOverdue() {
        return Overdue;
    }

    public void setOverdue(String overdue) {
        Overdue = overdue;
    }

    public int getBrownDay() {
        return BrownDay;
    }

    public void setBrownDay(int brownDay) {
        BrownDay = brownDay;
    }

    @Override
    public String toString() {
        return "BrownHistory{" +
                "Brownid='" + Brownid + '\'' +
                ", Num='" + Num + '\'' +
                ", BrownTime='" + BrownTime + '\'' +
                ", Bookid=" + Bookid +
                ", Return='" + Return + '\'' +
                ", Overdue='" + Overdue + '\'' +
                ", BrownDay=" + BrownDay +
                '}';
    }
}

class User{
    private int id;
    private String Name;
    private String Num;
    private String PassWord;
    private String Gender;
    private String Uclass;
    private String Tel;
    private String BrownHistory;
    private String ReturnHistory;
    private String OverdueHistory;

    public User(int id, String name, String num, String passWord, String gender, String aClass, String tel, String brownHistory, String returnHistory, String overdueHistory) {
        this.id = id;
        Name = name;
        Num = num;
        PassWord = passWord;
        Gender = gender;
        Uclass = aClass;
        Tel = tel;
        BrownHistory = brownHistory;
        ReturnHistory = returnHistory;
        OverdueHistory = overdueHistory;
    }
    public User (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getUclass() {
        return Uclass;
    }

    public void setClass(String aClass) {
        Uclass = aClass;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getBrownHistory() {
        return BrownHistory;
    }

    public void setBrownHistory(String brownHistory) {
        BrownHistory = brownHistory;
    }

    public String getReturnHistory() {
        return ReturnHistory;
    }

    public void setReturnHistory(String returnHistory) {
        ReturnHistory = returnHistory;
    }

    public String getOverdueHistory() {
        return OverdueHistory;
    }

    public void setOverdueHistory(String overdueHistory) {
        OverdueHistory = overdueHistory;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Num='" + Num + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Uclass='" + Uclass + '\'' +
                ", Tel='" + Tel + '\'' +
                ", BrownHistory='" + BrownHistory + '\'' +
                ", ReturnHistory='" + ReturnHistory + '\'' +
                ", OverdueHistory='" + OverdueHistory + '\'' +
                '}';
    }
}

