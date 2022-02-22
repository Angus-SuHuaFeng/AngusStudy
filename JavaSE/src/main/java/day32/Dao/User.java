package day32.Dao;

public class User {
//    属性的声明要和表中的字段对应
    private int sid;
    private String sname;
    private int sage;

    public User() {
    }

    public User(int sid, String sname, int sage) {
        this.sid = sid;
        this.sname = sname;
        this.sage = sage;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getSage() {
        return sage;
    }

    public void setSage(int sage) {
        this.sage = sage;
    }

    @Override
    public String toString() {
        return "User{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", sage=" + sage +
                '}';
    }
}
