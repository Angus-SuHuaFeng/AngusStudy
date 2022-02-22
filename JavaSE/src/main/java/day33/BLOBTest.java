package day33;

import day32.Dao.jdbcConnectFactory;

import java.io.*;
import java.sql.*;

public class BLOBTest {
    public static void main(String[] args) {
        writeBlob();
        readBlob();
    }
    public static void writeBlob(){
        Connection connection = null;
        PreparedStatement  preparedStatement = null;
        FileInputStream fis = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            preparedStatement = connection.prepareStatement("insert into employee values (?,?,?);");
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2,"picture1");
            fis = new FileInputStream("C:\\Users\\Angus\\Desktop\\a.jpg");
            preparedStatement.setBinaryStream(3,fis);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
                jdbcConnectFactory.close(preparedStatement,connection);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static void readBlob(){
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        FileOutputStream fos = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            preparedStatement = connection.prepareStatement("select * from employee");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            InputStream binaryStream = resultSet.getBinaryStream(3);
            fos = new FileOutputStream("C:\\Users\\Angus\\Desktop\\b.jpg");
            byte[] bytes = new byte[1024];
            int len;
            while ((len = binaryStream.read(bytes))!=-1){
                fos.write(bytes,0,len);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jdbcConnectFactory.close(preparedStatement,connection);
        }
    }
}
