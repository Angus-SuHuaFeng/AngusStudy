package day33;

import day32.Dao.jdbcConnectFactory;

import java.sql.*;

public class BatchTest {
    public static void main(String[] args) {
        /**
         *      1.相比单个SQL语句的处理，处理一个批处理中的多个SQL语句是一种更为有效的方式。
         *      2.一个批处理是被发送到数据库以作为单个单元执行的一组更新语句。
         *      3.void addBatch()：将一个SQL语句添加到一个批处理中。
         *      4.int executeBatch()：将一个SQL语句的批处理发送到数据库中以便进行处理并返回已更新的总行数。
         *      5.void clearBatch()： 从批处理中移除SQL语句。
         */
        Connection connection = null;
        Statement statement = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.addBatch("update student1 set sage=10 where sid=10");
            statement.addBatch("update student1 set sage=11 where sid=11");
            statement.addBatch("insert into student1 values (12,'Jerry',22)");
            statement.addBatch("insert into student1 values (13,'Zhang',20);");
            int[] ints = statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            jdbcConnectFactory.close(statement,connection);
        }
    }
}
