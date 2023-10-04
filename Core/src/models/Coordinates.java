package models;


import jakarta.xml.bind.annotation.XmlElement;
import validation.Ask;
import validation.MaxValue;

import java.io.Serializable;

public class Coordinates implements Serializable, Comparable<Coordinates> {
    @XmlElement
    @Ask
    private double x;
    @XmlElement
    @MaxValue(810)
    @Ask
    private int y; //Максимальное значение поля: 810

    public Coordinates(double x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinates() {

    }
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Coordinates o){
        return (int)(Math.sqrt(this.x*this.x + this.y*this.y) - Math.sqrt(o.x*o.x + o.y*o.y));
    }
    public void setX(double parseDouble) {
        this.x = parseDouble;
    }

    public void setY(int parseInt) {
        this.y = parseInt;
    }
}