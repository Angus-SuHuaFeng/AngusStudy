package Test01;

public class GenClassTest {
    public static void main(String[] args) {

    }
    public <T> T to(T t){
        return t;
    }
}

class Employee{
    private int eid;
    private String ename;
    private String gender;
    private double esalary;


    public Employee(int eid, String ename, String gender, double esalary) {
        this.eid = eid;
        this.ename = ename;
        this.gender = gender;
        this.esalary = esalary;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getEsalary() {
        return esalary;
    }

    public void setEsalary(double esalary) {
        this.esalary = esalary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eid=" + eid +
                ", ename='" + ename + '\'' +
                ", gender='" + gender + '\'' +
                ", esalary=" + esalary +
                '}';
    }


}
