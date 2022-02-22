package day33;

import com.mysql.cj.jdbc.JdbcConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class jdbcPropertiesTest {
    public static void main(String[] args) throws IOException, SQLException {
        Connection con = null;
        Statement statement = null;
        Properties properties = new Properties();
        properties.load(new FileInputStream("E:\\IDEADemo\\javaEE\\src\\main\\java\\day32\\db.properties"));
        String driver = properties.getProperty("driverName");
        String url = properties.getProperty("url");
//        String user = properties.getProperty("user");
//        String password = properties.getProperty("password");
//        通过getConnection(url,pro)来实现数据库连接
        try {
            Class.forName(driver);
            System.out.println("驱动注册完成");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con = DriverManager.getConnection(url, properties);
        statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student1");
        if (resultSet.next()){
            System.out.println(resultSet.getInt(1)+"\t"+ resultSet.getString(2)+"\t"+resultSet.getInt(3));
        }
        if (con!=null) con.close();
        if (statement!=null) statement.close();
    }

}
