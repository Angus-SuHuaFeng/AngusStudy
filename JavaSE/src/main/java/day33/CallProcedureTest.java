package day33;

import day32.Dao.jdbcConnectFactory;

import java.sql.*;

public class CallProcedureTest {
    public static void main(String[] args) {
//        调用存储过程
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            callableStatement = connection.prepareCall("{call myProcedure(?, ?, ?)}");
            callableStatement.setInt(1,9);
            callableStatement.setString(2,"Nick");
            callableStatement.setInt(3,19);
            callableStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            jdbcConnectFactory.close(callableStatement,connection);
        }
    }
}
