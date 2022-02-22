package day32.Dao;

import java.util.List;

public class UserTest {
    public static void main(String[] args) {
        userDaoImpl userDao = new userDaoImpl();
        userDao.addUser(new User(userDao.getNewUserID(),"Angus",21));
        List<User> users = userDao.selectAllUser();
        System.out.println(users);
        System.out.println("用户更新结果："+userDao.updateUser(new User(2, "John", 20)));
        System.out.println("查询结果为："+userDao.selectUser(2));
        System.out.println("删除操作执行结果："+userDao.deleteUser(4));
        System.out.println("下次添加用户的序号为："+userDao.getNewUserID());
    }
}
