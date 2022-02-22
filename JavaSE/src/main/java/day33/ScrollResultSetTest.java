package day33;

import day32.Dao.jdbcConnectFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollResultSetTest {
    public static void main(String[] args) {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
            resultSet = statement.executeQuery("select * from student1");
            while (resultSet.next()){
                showInfo(resultSet);
            }
//            使用可滚动结果集
            resultSet.first();          //将游标置于首位
            showInfo(resultSet);
            resultSet.last();           //将游标置于最后一位
            showInfo(resultSet);
            resultSet.absolute(1); //绝对位置，定位到第一条
            showInfo(resultSet);
            resultSet.relative(3);//相对位置，相对于当前位置，往后移动三条
            showInfo(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            jdbcConnectFactory.close(resultSet,statement,connection);
        }
    }

    public static void showInfo(ResultSet resultSet) throws SQLException {
        System.out.println("ID="+resultSet.getInt(1)+"\tname="
                + resultSet.getString(2)
                +"\tage="+resultSet.getInt(3));
    }
}
