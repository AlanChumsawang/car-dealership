package com.pluralsight.cardealership.model;

import java.util.ArrayList;

public class Dealership {
    private int id;
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;
    private ArrayList<Contract> contractsList;

    public Dealership(int id, String name, String address, String phone) {
       this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<Vehicle>();
        this.contractsList = new ArrayList<Contract>();
    }

    public void addVehicle(Vehicle vehicle) {
        inventory.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        inventory.remove(vehicle);
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return inventory;
    }

    public Vehicle getVehicleByVin(int vin) {
        for (Vehicle vehicle : inventory) {
            if (vehicle.getVin() == vin) {
                return vehicle;
            }
        }
        return null;
    }

    public ArrayList<Vehicle> getVehicleByPrice(double min, double max) {
        ArrayList<Vehicle> vehiclesWithinPriceRange = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getPrice() >= min && vehicle.getPrice() <= max) {
                vehiclesWithinPriceRange.add(vehicle);
            }
        }
            return vehiclesWithinPriceRange; // Return the list of vehicles within the price range
        }

    public ArrayList<Vehicle> getVehicleByMakeModel(String make, String model){
        ArrayList<Vehicle> vehicleOfMakeModel = new ArrayList<Vehicle>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getMake().equalsIgnoreCase(make) && vehicle.getModel().equalsIgnoreCase(model)) {
                vehicleOfMakeModel.add(vehicle);
            }
        }
        return vehicleOfMakeModel;
    }

    public ArrayList<Vehicle> getVehicleByYear(int year){
        ArrayList<Vehicle> vehicleOfYear = new ArrayList<Vehicle>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getYear() == year) {
                vehicleOfYear.add(vehicle);
            }
        }
        return vehicleOfYear;
    }

    public ArrayList<Vehicle> getVehicleByColor(String color){
        ArrayList<Vehicle> vehicleOfColor = new ArrayList<Vehicle>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getColor().equalsIgnoreCase(color)) {
                vehicleOfColor.add(vehicle);
            }
        }
        return vehicleOfColor;
    }

    public ArrayList<Vehicle> getVehicleByMileage(int odometer){
        ArrayList<Vehicle> vehiclesWithinMileage = new ArrayList<Vehicle>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getOdometer() <= odometer) {
                vehiclesWithinMileage.add(vehicle);
            }
        }
        return vehiclesWithinMileage;
    }

    public ArrayList<Vehicle> getVehicleByType(String type){
        ArrayList<Vehicle> vehicleOfType = new ArrayList<Vehicle>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getType().equalsIgnoreCase(type)) {
                vehicleOfType.add(vehicle);
            }
        }
        return vehicleOfType;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


    public ArrayList<Contract> getContractsList() {
        return contractsList;
    }

    public void addContract(Contract contract) {
        contractsList.add(contract);
    }

    public ArrayList<Contract> getLast10Contracts() {
        ArrayList<Contract> last10Contracts = new ArrayList<Contract>();
        int size = contractsList.size();
        if (size <= 10) {
            return contractsList;
        } else {
            for (int i = size - 10; i < size; i++) {
                last10Contracts.add(contractsList.get(i));
            }
            return last10Contracts;
        }
    }

    @Override
    public String toString() {
        return "Dealership{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", inventory=" + inventory +
                '}';
    }
}
