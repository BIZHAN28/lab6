package models;


import jakarta.xml.bind.annotation.XmlAttribute;

import validation.*;

import java.io.Serializable;

public class HumanBeing implements Comparable<HumanBeing> , Serializable {
    @GreaterThanZero
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    @NotEmpty
    @Ask
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    @Ask
    private Coordinates coordinates; //Поле не может быть null
    @NotNull
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    @Ask
    private boolean realHero; //Поле не может быть null
    @Ask
    private boolean hasToothpick;
    @NotNull
    @MaxValue(689)
    @Ask
    private Integer impactSpeed; //Максимальное значение поля: 689, Поле может быть null
    @NotNull
    @Ask
    private WeaponType weaponType; //Поле может быть null

    @NotNull
    @Ask
    private Mood mood; //Поле может быть null
    @NotNull
    @Ask
    private Car car; //Поле не может быть null
    public HumanBeing(String name, Coordinates coordinates, boolean realHero, boolean hasToothpick, Integer impactSpeed, WeaponType weaponType, Mood mood, Car car){

        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = java.time.LocalDate.now();
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }
    public HumanBeing(){
        //this.id = CollectionManager.generateId();
        //this.creationDate = java.time.LocalDate.now();
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = java.time.LocalDate.parse(creationDate);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {this.name = name;}

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    @XmlAttribute
    public String getCreationDate() {
        return creationDate.toString();
    }

    public boolean isRealHero() {
        return realHero;
    }

    public boolean isHasToothpick() {
        return hasToothpick;
    }

    public Integer getImpactSpeed() {
        return impactSpeed;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Mood getMood() {
        return mood;
    }
    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "HumanBeing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", realHero=" + realHero +
                ", hasToothpick=" + hasToothpick +
                ", impactSpeed=" + impactSpeed +
                ", weaponType=" + weaponType +
                ", mood=" + mood +
                ", car=" + car +
                '}';
    }


    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public void setCreationDate(java.time.LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }
    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }
    public void setImpactSpeed(Integer impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCoordinateX(double parseDouble) {
        coordinates.setX(parseDouble);
    }

    public void setCoordinateY(int parseInt) {
        coordinates.setY(parseInt);
    }
    @Override
    public int compareTo(HumanBeing o) {
        return this.impactSpeed.compareTo(o.impactSpeed);
    }
}
