package day32;

import java.sql.*;

public class SqlTest {
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
//            3.获取Statement对象(注意最好将Statement对象创建在外面，方便关闭资源 )
            statement = con.createStatement();
//            插入操作
//            String sql1 = "insert into student1 values (?,?,?)";
//            PreparedStatement preparedStatement = con.prepareStatement(sql1);
//            preparedStatement.setInt(1,8);
//            preparedStatement.setString(2,"John");
//            preparedStatement.setInt(3,20);
////            update表示影响了几条纪录
//            int update = preparedStatement.executeUpdate();
//            System.out.println(update);

//            修改操作
//            String sql2 = "update student1 set sage = ? where sid = ?";
//            PreparedStatement preparedStatement1 = con.prepareStatement(sql2);
//            preparedStatement1.setInt(1,21);
//            preparedStatement1.setInt(2,8);
//            int i = preparedStatement1.executeUpdate();
//            System.out.println("影响了"+i+"行数据");
//            删除操作
//            String sql3 = "delete from student1 where sid = ?";
//            PreparedStatement preparedStatement = con.prepareStatement(sql3);
//            preparedStatement.setInt(1,1);
//            int i = preparedStatement.executeUpdate();
//            System.out.println("影响了"+i+"行数据");
//            查询操作
//            String sql4 = "select * from student1 where sid = ?";
//            PreparedStatement preparedStatement = con.prepareStatement(sql4);
//            preparedStatement.setInt(1,2);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                System.out.println("sid = "+resultSet.getInt(1)+"\tsname="+resultSet.getString(2)+"\tsage="+resultSet.getInt(3));
//            }
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

