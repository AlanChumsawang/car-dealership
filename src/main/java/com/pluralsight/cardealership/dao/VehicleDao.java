package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.model.Vehicle;

import java.util.List;

public interface VehicleDao {
    List<Vehicle> findAllVehicles();
    Vehicle findVehicleByVin(int vin);
    void addVehicle(Vehicle vehicle);
    void deleteVehicle(Integer vin);
    void updateVehicle(Vehicle vehicle, int oldVin);
}