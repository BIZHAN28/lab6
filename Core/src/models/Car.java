package models;

import validation.Ask;

import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {
    @Ask
    private boolean cool;

    public Car(boolean  cool) {
        this.cool = cool;
    }
    public Car(){

    }

    public void setCool(boolean cool) {
        this.cool = cool;
    }
    public boolean getCool(){
        return cool;
    }

    @Override
    public String toString() {
        return "Car{" +
                "cool=" + cool +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        Car car = (Car) obj;
        return car.getCool() == cool;
    }
    @Override
    public int hashCode() {
        return Objects.hash(cool);
    }
}