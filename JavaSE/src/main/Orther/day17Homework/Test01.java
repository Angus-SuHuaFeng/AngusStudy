package day17Homework;

import java.util.Objects;

public class Test01 {
    public static void main(String[] args) {
        Car car = new Car("五菱宏光");
        System.out.println(car.NoOfWheels());
        Motorbike motorbike = new Motorbike("川崎H2R");
        System.out.println(motorbike.NoOfWheels());
    }
}
abstract class Vehicle{
    String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "brand='" + brand + '\'' +
                '}';
    }

    public Vehicle(String brand) {
        this.brand = brand;
    }

    public Vehicle(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(brand, vehicle.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand);
    }

    public abstract String NoOfWheels();
}
class Car extends Vehicle{

    public Car(String brand) {
        super(brand);
    }

    public Car() {
    }

    @Override
    public String NoOfWheels() {
        return this.brand+"四轮车";
    }
}

class Motorbike extends Vehicle{
    public Motorbike(String brand) {
        super(brand);
    }

    public Motorbike() {
    }

    @Override
    public String NoOfWheels() {
        return this.brand+"双轮车";
    }
}