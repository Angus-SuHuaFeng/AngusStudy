package day14;

public class test14 {
    public static void main(String[] args) {
        Perso ming = new Perso();
        Perso hong = new Perso();
        ming.setName("Xiao Ming");
        // TODO: 给Person增加重载方法setName(String, String):
        hong.setName("xiao","Ming");
        System.out.println(ming.getName());
        System.out.println(hong.getName());
    }
}
class Perso {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setName(String xing,String ming){
        this.name=xing+ming;
    }
}

