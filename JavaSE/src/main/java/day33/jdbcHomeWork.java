package day33;

import day32.Dao.jdbcConnectFactory;

import java.sql.*;
import java.util.Scanner;

public class jdbcHomeWork {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = jdbcConnectFactory.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            建表
            statement.executeUpdate("create table User1(name varchar(10) primary key,Pwd varchar(6) not null,Email varchar(64),Birthday DATE)ENGINE=INNODB DEFAULT CHARSET=utf8;");
            System.out.println("创建表成功");
//            插入新纪录（小王 111111 xiaowang@163.com 19850608）
            statement.executeUpdate("insert into User1 values ('小王', '111111', 'xiaowang@163.com', 19850608);");
            statement.executeUpdate("insert into User1 values ('张四', '111112', 'xiaowang@163.com', 19850608);");
            System.out.println("插入后结果:");
            resultSet = statement.executeQuery("select * from User1;");
            showInfo(resultSet);
            System.out.println("请输入要修改的姓名和新密码，空格分隔");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            String[] s = str.split(" ");
            changePwd(connection,s[0],s[1]);
            System.out.println("执行删除操作: ");
            statement.executeUpdate("delete from User1 where name='张四';");
            connection.commit();
            resultSet = statement.executeQuery("select * from User1;");
            showInfo(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            jdbcConnectFactory.close(statement,connection);
        }
    }
    public static void showInfo(ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            System.out.println("name: "+resultSet.getString(1)+"\tPWD: "
                    + resultSet.getString(2)+"\tEmail: "
                    + resultSet.getString(3)+"\tBirth: "
                    + resultSet.getDate(4));
        }
    }

    public static void changePwd(Connection connection, String name, String newPwd){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("update user set Pwd = ? where Name=?");
            preparedStatement.setString(1,newPwd);
            preparedStatement.setString(2,name);
            int i = preparedStatement.executeUpdate();
            if (i==1){
                System.out.println("修改了1条数据");
            }else {
                System.out.println("修改失败,请重新修改");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }
}
