package day33;

import day32.Dao.jdbcConnectFactory;

import java.sql.*;

public class MetaDataTest {
    public static void main(String[] args) {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println("数据库产品名: "+metaData.getDatabaseProductName());
            System.out.println("数据库版本号: "+metaData.getDatabaseProductVersion());
            System.out.println("驱动器: "+metaData.getDriverName());
            System.out.println("驱动器版本号: "+ metaData.getDriverVersion());
            System.out.println("是否支持不可滚动（只读）结果集(TYPE_FORWARD_ONLY): "+metaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
            System.out.println("是否支持可滚动不敏感结果集(TYPE_SCROLL_INSENSITIVE): "+ metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
            System.out.println("是否支持可滚动敏感结果集(TYPE_SCROLL_SENSITIVE): "+ metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
            System.out.println("CONCUR_READ_ONLY: "+metaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY));
            System.out.println("CONCUR_UPDATABLE: "+metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE));
            System.out.println("url: "+metaData.getURL());
            String sql = "select * from student1";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            System.out.println("结果集总列数: "+resultSetMetaData.getColumnCount());
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                System.out.println("第"+i+"列列名: "+resultSetMetaData.getColumnName(i)+"\t类型: "+resultSetMetaData.getColumnTypeName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
