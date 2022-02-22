package day27;

import java.sql.*;

/**
 * @author Angus
 */
public class JdbcTest {
    public static void main(String[] args) {
        //加载驱动类
        //Class.forName用于将类加载到JVM内存中
        //访问不同的数据库需要加载不同的驱动类
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //数据库连接url,用于配置参数:主机名,端口,数据库名称,编码格式
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        //用户名
        String username = "root";
        String password = "root";
        Statement statement=null;
        Connection con=null;
        //DriverManager.getConnection用于获得数据库连接对象
        try {
            con = DriverManager.getConnection(url, username, password);
            //创建一个statement对象，用于执行sql语句
            statement = con.createStatement();
            String s = "张";
            String sql = "select * from student1 where sname like '%"+s+"%'";
            //执行更新语句
//            statement.execute("update student1 set sage = 20 where sname ='张三'");
            //执行查询语句，得到一个结果集resultSet对象
            ResultSet resultSet = statement.executeQuery(sql);
            //遍历结果集
            while (resultSet.next()){
                int sid = resultSet.getInt(1);
                String sname = resultSet.getString(2);
                int sage = resultSet.getInt(3);
                System.out.println(sid+","+sname+","+sage);
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
