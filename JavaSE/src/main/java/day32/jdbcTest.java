package day32;

import java.sql.*;

public class jdbcTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai";
        String usr = "root";
        String password = "root";
        Connection con = null;
        Statement statement = null;
//        1.注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("驱动注册完成");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
//            2.建立连接
            con = DriverManager.getConnection(url,usr,password);
//            获取Statement对象(注意最好将Statement对象创建在外面，方便关闭资源 )
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from student");
            while (resultSet.next()){
                System.out.println("ID"+resultSet.getInt(1)+"\tname"+ resultSet.getString(2)+"\tage"+resultSet.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭statement语句对象
            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //关闭连接
            if (con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}

