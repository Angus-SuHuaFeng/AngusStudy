package day33;

import day32.Dao.jdbcConnectFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommitTest {
    public static void main(String[] args) {
        /**
         * 设定成非自动提交可以在事务中某一条语句出现错误时进行回滚
         */
        Connection connection = null;
        Statement statement = null;
        try {
            connection = jdbcConnectFactory.getConnection();
//            设置事务管理模式为显式提交模式(非自动提交模式)
            connection.setAutoCommit(false);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
            statement.executeUpdate("insert into student1 values (10,'mike',24)");
            statement.executeUpdate("insert into student1 values (11,'Tom',21)");
            statement.executeUpdate("insert into student1 values (9,'Kit',20)");
//            提交事务
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
//                如果sql语句在执行过程中出现错误,则在catch中执行事务回滚
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            jdbcConnectFactory.close(statement,connection);
        }
    }
}
