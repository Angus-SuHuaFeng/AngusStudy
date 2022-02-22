package day1011;

public class RabbitAndTurtle extends Thread{
    int Race;
    int TurtleRance;
    static float flag;

//    public RabbitAndTurtle(int rabbitRace, int turtleRance, float x) {
//        RabbitRace = rabbitRace;
//        TurtleRance = turtleRance;
//        flag = x;
//    }

    public RabbitAndTurtle(int race, float flag) {
        Race = race;
        RabbitAndTurtle.flag = flag;
    }

    @Override
    public void run() {
        if (flag<0.3){
            Race += 2;
            day1011.Race.setRabbitRace();
            System.out.println("兔子跑了"+Race+"米了");
        }
        else {
            Race++;
            day1011.Race.setTurtleRace();
            System.out.println("乌龟跑了"+Race+"米了");
        }
    }

}
