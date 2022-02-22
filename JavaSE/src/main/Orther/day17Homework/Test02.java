package day17Homework;

public class Test02 {
    public static void main(String[] args) {
        Bus bus = new Bus();
        bus.charge();
        Taxi taxi = new Taxi();
        taxi.charge();
        Cinema cinema = new Cinema();
        cinema.charge();
        cinema.play();
    }
}
interface Charge{
    abstract void charge();
}
interface Play{
    abstract void play();
}

class Bus implements Charge{

    @Override
    public void charge() {
        System.out.println("公共汽车：1元/张，不计公里数");
    }
}

class Taxi implements Charge{

    @Override
    public void charge() {
        System.out.println("出租车：1.6元/公里，起价3公里。");
    }
}

class Cinema implements Charge,Play{
    @Override
    public void charge() {
        System.out.println("解放电影院：30元/张，凭学生证享受半价。");
    }

    @Override
    public void play() {
        System.out.println("正在放映电影。");
    }
}