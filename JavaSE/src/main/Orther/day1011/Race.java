package day1011;

import java.util.Random;

public class Race {
    static int Rabbit = 0;
    static int Turtle = 0;
    public static void main(String[] args) {
        float flag;

        while (true){
            Random random = new Random();
            flag = random.nextFloat();
            RabbitAndTurtle rabbitAndTurtle1 = new RabbitAndTurtle(Rabbit, flag);
            RabbitAndTurtle rabbitAndTurtle2 = new RabbitAndTurtle(Turtle, flag);
            rabbitAndTurtle1.start();
            rabbitAndTurtle2.start();
            if (Rabbit>=100){
                System.out. println("兔子赢了");
                System.exit(0);
            }else if (Turtle>=100){
                System.out.println("乌龟赢了");
                System.exit(0);
            }
        }
    }
    public static void setRabbitRace(){
        Rabbit+=2;
    }
    public static void setTurtleRace(){
        Turtle++;
    }
}
