package day32.Dao;

import java.sql.*;

public class jdbcConnectFactory {
    static String url = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai";
    static String usr = "root";
    static String password = "root";
    static String DriverName = "com.mysql.cj.jdbc.Driver";
//    获取连接
    public static Connection getConnection(String url, String usr, String password, String DriverName){
        try {
            Class.forName(DriverName);
            return DriverManager.getConnection(url,usr,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Connection getConnection(){
        try {
            Class.forName(DriverName);
            return DriverManager.getConnection(url,usr,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(ResultSet resultSet, Statement statement,Connection connection){
        try {
            if (resultSet!=null)resultSet.close();
            if (statement!=null)statement.close();
            if (connection!=null)connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void close(Statement statement,Connection connection){
        try {
            if (statement!=null)statement.close();
            if (connection!=null)connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
