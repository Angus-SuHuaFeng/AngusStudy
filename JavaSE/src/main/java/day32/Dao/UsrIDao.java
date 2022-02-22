package day32.Dao;

import java.sql.ResultSet;
import java.util.List;

public interface UsrIDao {
//    1.获得唯一标识id
    public int getNewUserID();
//    2.添加用户
    public boolean addUser(User user);
//    3.修改用户信息
    public boolean  updateUser(User user);
//    4.删除用户
    public boolean deleteUser(int userId);
//    5.查询所有用户
    public List<User> selectAllUser();
//    6.查询指定id用户
    public User selectUser(int userId);
}
