package HomeWork;

import java.util.*;

public class GenHomeWork{
    public static void main(String[] args) {
        DAO<User> userDAO = new DAO<>();
//        测试save
        userDAO.save("1",new User(1,20,"张三"));
        userDAO.save("2",new User(2,21,"张四"));
        userDAO.save("3",new User(3,22,"张五"));
        userDAO.save("4",new User(4,19,"张六"));
        userDAO.save("5",new User(5,20,"张七"));
        System.out.println("测试save");
        System.out.println(userDAO.list());
//        测试get
        System.out.println("测试get");
        System.out.println(userDAO.get("1"));
        System.out.println(userDAO.get("10"));
//        测试update
        userDAO.update("1",new User(1,19,"苏华锋"));
        System.out.println("测试update");
        System.out.println(userDAO.list());
//        测试delete
        userDAO.delete("3");
//        测试list
        System.out.println("测试list");
        System.out.println(userDAO.list());
    }
}

class DAO<T>{
    Map<String, T> map = new HashMap<>();

    public void save(String id,T entity){
        map.put(id, entity);
    }

    public T get(String id){
//        if(map.get(id)!=null){      //containsKey()可以判断Key是否存在
//            return map.get(id);
//        }else {
//            return null;
//        }
//        getOrDefault()
//           :the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key
        return map.getOrDefault(id, null);
    }

    public void update(String id, T entity){
        map.replace(id, entity);
    }

    public List<T> list(){
        Collection<T> collection = map.values();
        if (collection.isEmpty()){
            return null;
        }
        List<T> aList = new ArrayList(collection);  //直接用构造方法传入collection
//        Set<Map.Entry<String, T>> entries = map.entrySet();
//        for (Map.Entry<String, T> entry: entries) {
//            aList.add(entry.getValue());
//        }
        return aList;
    }

    public void delete(String id){
        map.remove(id);
    }
}
class User{
    private int id;
    private int age;
    private String name;

    public User() {
    }

    public User(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}