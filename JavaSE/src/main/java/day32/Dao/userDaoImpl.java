package day32.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userDaoImpl implements UsrIDao{

    @Override
    public int getNewUserID() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int num = 0;
        try {
            connection = jdbcConnectFactory.getConnection();
            statement = connection.createStatement();
            String sql = "select max(sid) from student1";
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                num = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num+1;
    }

    @Override
    public boolean addUser(User user) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            statement = connection.createStatement();
            String sql = "insert into student1 values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,user.getSid());
            preparedStatement.setString(2,user.getSname());
            preparedStatement.setInt(3,user.getSage());
            int i = preparedStatement.executeUpdate();
            System.out.println("影响了"+i+"行数据");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        Connection connection = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            String sql = "update student1 set sname= ? , sage=? where sid=?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getSname());
            preparedStatement.setInt(2,user.getSage());
            preparedStatement.setInt(3,user.getSid());
            int i = preparedStatement.executeUpdate();
            System.out.println("影响了"+i+"行数据");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            statement = connection.createStatement();
            String sql = "delete from student1 where sid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            int i = preparedStatement.executeUpdate();
            System.out.println("影响了"+i+"行数据");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> selectAllUser() {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            statement = connection.createStatement();
            String sql = "select * from student1";
            resultSet = statement.executeQuery(sql);
            int i=0;
            while (resultSet.next()){
                users.add(new User(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3)));
                i++;
            }
            System.out.println("查询到"+i+"行数据");
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User selectUser(int userId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcConnectFactory.getConnection();
            statement = connection.createStatement();
            String sql = "select * from student1 where sid=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                User user = new User(userId, resultSet.getString(2), resultSet.getInt(3));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
