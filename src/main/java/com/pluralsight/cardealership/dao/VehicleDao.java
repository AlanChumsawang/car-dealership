package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.model.Vehicle;

import java.util.List;

public interface VehicleDao {
    List<Vehicle> findAllVehicles();

    List<Vehicle> findVehicleByMakeModel(String make, String model);

    List<Vehicle> findVehicleByPrice(double minPrice, double maxPrice);

    List<Vehicle> findVehicleByYear(int year);

    List<Vehicle> findVehicleByColor(String color);

    List<Vehicle> findVehicleByMileage(int mileage);

    List<Vehicle> findVehicleByType(String type);

    Vehicle findVehicleByVin(int vin);

    void addVehicle(Vehicle vehicle);

    void deleteVehicle(Integer vin);

    void updateVehicle(Vehicle vehicle, int oldVin);
}
