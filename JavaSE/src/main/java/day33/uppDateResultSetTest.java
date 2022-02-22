package day33;

import day32.Dao.jdbcConnectFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class uppDateResultSetTest {
    public static void main(String[] args) {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("select * from student1");
            while (resultSet.next()){
                showInfo(resultSet);
            }
//            使用可更新结果集
            System.out.println("更新后");
//            回到最初的位置
            resultSet.beforeFirst();
            while (resultSet.next()){
                String name = resultSet.getString("sname");
                if (name.equals("Angus")){
                    resultSet.updateInt(3,10);
                    resultSet.updateRow();
                }
            }
            resultSet.beforeFirst();
            while (resultSet.next()){
                showInfo(resultSet);
            }
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
