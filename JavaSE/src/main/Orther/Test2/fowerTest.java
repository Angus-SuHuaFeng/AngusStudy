package Test2;

import java.util.Random;

public class fowerTest {
    public static void main(String[] args) {
//        Flower flower = new Flower("白色",10);
//        System.out.println(flower.showInfo());
//        Rose rose = new Rose("紫色",30,"大理");
//        System.out.println(rose.showInfo());
        Flower a = new Rose("红色",20,"d");
        System.out.println(a.showInfo());

//        rose.warn();
    }
}

class Flower{
    private String color;
    private double price;

    public Flower(String color, double price) {
        this.color = color;
        this.price = price;
    }

    public Flower() { }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String showInfo() {
        return "花的颜色是:" + color +
                ", " + "价格是:" + price;
    }
}

class Rose extends Flower{
    private String production;

    public Rose(String color, double price, String production) {
        super(color, price);
        this.production = production;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    @Override
    public String showInfo() {
        return super.showInfo() + " , " + "产地是:" + getProduction();
    }

    public void warn(){
        System.out.println("不要摘玫瑰花，小心扎手！");
    }
}