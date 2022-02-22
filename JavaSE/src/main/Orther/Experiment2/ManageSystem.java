package Experiment2;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import day32.Dao.jdbcConnectFactory;
/**
 * @author Angus
 */
public class ManageSystem {
    static String url = "jdbc:mysql://localhost:3306/student?serverTimezone=Asia/Shanghai";
    static String usr = "root";
    static String password = "root";
    static String DriverName = "com.mysql.cj.jdbc.Driver";
    public static void main(String[] args){
        Connection connection = jdbcConnectFactory.getConnection(url,usr,password,DriverName);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table if not exists student(num varchar(12) primary key ,name varchar(6) ,gender varchar(4) ,birty varchar (10))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Print(connection);
    }

    public static void Print(Connection connection){
        while(true){
            System.out.println("功能选择【   1.添加学员    2.删除学员    3.修改    4.查询    5.列出全部学生    0.退出   】");
            switch(print()){
                case 1:
                    addStudent(connection);
                    break;
                case 2:
                    delStudent(connection);
                    break;
                case 3:
                    changeStudent(connection);
                    break;
                case 4:
                    serchStudent(connection);
                    break;
                case 5:
                    listStudent(connection);
                    break;
                default:
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
            }
        }
    }

    public static void addStudent(Connection connection) {
        System.out.print("请输入要添加的学生学号:");
        String s = readTool.readNum();
        System.out.print("请输入要添加的学生姓名:");
        String s1 = readTool.readName();
        System.out.print("请输入要添加的学生性别:");
        String s2 = readTool.readGender();
        System.out.print("请输入要添加的学生出生日期:");
        String s3 = readTool.readBirth();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into student values (?,?,?,?)");
            preparedStatement.setString(1,s);
            preparedStatement.setString(2,s1);
            preparedStatement.setString(3,s2);
            preparedStatement.setString(4,s3);
            int i = preparedStatement.executeUpdate();
            System.out.println("成功添加"+i+"个学生");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void serchStudent(Connection connection){
        System.out.print("请输入要查询学生的学号:");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from student where num = ?");
            preparedStatement.setString(1,str);
            ResultSet resultSet = preparedStatement.executeQuery();
            int flag = 1;
            while (resultSet.next()){
                System.out.println("学号: "+resultSet.getString(1)+"\t姓名: "+resultSet.getString(2)+"\t性别: "+resultSet.getString(3)+"\t生日: "+resultSet.getString(4));
                flag = 0;
            }
            if (flag==1){
                System.out.println("没有查询到该学生");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void delStudent(Connection connection){
        System.out.print("请输入要删除学生的学号");
        String str;
        str = readTool.readNum();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from student where num=?");
            preparedStatement.setString(1,str);
            int i = preparedStatement.executeUpdate();
            System.out.println("删除了"+i+"条数据");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void changeStudent(Connection connection){
        System.out.print("请输入要修改学生的学号");
        String str;
        str = readTool.readNum();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from student where num=?");
            preparedStatement.setString(1,str);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                System.out.println("要修改的学生不存在，请先添加该学生!");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("1.修改姓名      2.修改性别      3.修改出生日期      0.保存退出");
        while (true){
            String s = null;
            int n;
            n = readTool.readN();
            if(n==0){
                break;
            }else {
                switch (n){
                    case 1:
                        System.out.print("请输入姓名:");
                        s = readTool.readName();
                        change(connection,"name",s);
                        break;
                    case 2:
                        System.out.print("请输入性别:");
                        s = readTool.readGender();
                        change(connection,"gender",s);
                        break;
                    case 3:
                        System.out.print("请输入出生日期:");
                        s = readTool.readBirth();
                        change(connection,"birth",s);
                        break;
                    default:
                        break;
                }
            }
            System.out.println("*******如需继续修改请输入修改序号，修改完成请输入0退出*******");
        }
    }
    public static void change(Connection connection,String sx,String value){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update student set "+sx+"=?");
            preparedStatement.setString(1,value);
            int i = preparedStatement.executeUpdate();
            System.out.println("成功修改了"+i+"条数据");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void listStudent(Connection connection) {
        int i=0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from student");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                i++;
                System.out.println("学号: "+resultSet.getString(1)+"\t姓名: "+resultSet.getString(2)+"\t性别: "+resultSet.getString(3)+"\t生日: "+resultSet.getString(4));
            }
            System.out.println("共计"+i+"名学生");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int print(){
        int a;
        a =readTool.readN();
        return a;
    }
}

class readTool {
    static Scanner sc = new Scanner(System.in);
    public static String readLine(){
        String str = sc.nextLine();
        return str;
    }
    //读入序号
    static Scanner sc1 = new Scanner(System.in);
    public  static int readN(){
        int n;
        n = sc1.nextInt();
        return n;
    }
    //读入学号
    public static String readNum(){
        String n;
        while(true){
            n = readLine();
            Pattern pattern = Pattern.compile("^[0-9]\\d*|0$");
            Matcher isNum = pattern.matcher(n);
            if(!isNum.matches()){
                System.out.println("学号格式不正确，请重新输入");
            }else {
                break;
            }
        }
        return n;
    }
    //读入姓名
    public static String readName(){
        String name = readLine();
        return name;
    }

    //读入性别
    public static String readGender(){
        String g ;
        while(true){
            g = readLine();
            if(!("男".equals(g)|| "女".equals(g))){
                System.out.print("性别格式不正确,请重新输入:");
            }else{
                break;
            }
        }
        return g;
    }

    //读入出生日期
    public static String readBirth(){
        String birth ;
        while(true){
            birth = readLine();
            if(!isValidDate(birth)){
                System.out.print("日期格式不正确,请重新输入:");
            }else {
                break;
            }
        }
        return birth;
    }
    public static boolean isValidDate(String str) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = (Date)formatter.parse(str);
            return str.equals(formatter.format(date));
        }catch(Exception e){
            return false;
        }
    }
}