// Vehicle.java
package com.pluralsight.cardealership.model;

public class Vehicle {

    private int vin;
    private final int year;
    private final String make;
    private final String model;
    private final String type;
    private final String color;
    private final int odometer;
    private final double price;
    private final boolean isSold;

    public Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price, boolean isSold) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.type = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
        this.isSold = isSold;
    }

    public int getVin() {
        return vin;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getOdometer() {
        return odometer;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public boolean isSold(){ return isSold; }

    public void setVin(int vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vin=" + vin +
                ", year=" + year +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", vehicleType='" + type + '\'' +
                ", color='" + color + '\'' +
                ", odometer=" + odometer +
                ", price=" + price +
                '}';
    }
}