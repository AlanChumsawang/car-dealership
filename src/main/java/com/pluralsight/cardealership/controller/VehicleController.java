package com.pluralsight.cardealership.controller;

import com.pluralsight.cardealership.dao.VehicleDao;
import com.pluralsight.cardealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
Entry point for all vehicle-related operations annotated with @RestController so that Spring Boot knows to
create an instance of this class and map it to the appropriate URL path. The @RequestMapping annotation specifies
that this controller will handle all requests that start with /vehicles.
 */
@RestController
@RequestMapping(path= "/vehicles")
public class VehicleController {
    private final VehicleDao vehicleDao;

    /*
    VehicleDao is injected into the constructor using the @Autowired annotation. This is a form of dependency
    injection that allows Spring Boot to automatically create an instance of VehicleDao and pass it to the
    constructor when creating an instance of VehicleController.
     */
    @Autowired
    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    /*
    The methods below are CRUD operations for vehicles. Each method is annotated with the appropriate HTTP method
    Starting with CREATE or POST, READ or GET, UPDATE or PUT, and DELETE.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        vehicleDao.addVehicle(vehicle);
        return vehicle;
    }

    /*
    I created this standalone method to find a vehicle by its VIN. The method is useful for other methods that
    need to find a vehicle by its VIN.
     */

    @RequestMapping(path = "/{vin}")
    @ResponseStatus(code = HttpStatus.OK)
    public Vehicle findByVin(@PathVariable("vin") Integer vehicleVin) {
        List<Vehicle> vehicleList = vehicleDao.findAllVehicles();
        for (Vehicle v : vehicleList) {
            if (v.getVin() == vehicleVin) {
                return v;
            }
        }
        return null;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<Vehicle> findAll() {
        return vehicleDao.findAllVehicles();
    }

    @DeleteMapping(path = "/delete/{vin}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable("vin") Integer vehicleVin) {
        vehicleDao.deleteVehicle(vehicleVin);
    }

    @PutMapping(path = "/update/{vin}")
    @ResponseStatus(code = HttpStatus.OK)
    public Vehicle updateVehicle(@PathVariable("vin") Integer oldVin, @RequestBody Vehicle updatedVehicle) {
        Vehicle existingVehicle = vehicleDao.findVehicleByVin(oldVin);
        if (existingVehicle != null) {
            vehicleDao.updateVehicle(updatedVehicle, oldVin);
            return updatedVehicle;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
    }

    @GetMapping("/search")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Vehicle> searchVehicles(
            @RequestParam(required = false) Integer vin,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer minMiles,
            @RequestParam(required = false) Integer maxMiles,
            @RequestParam(required = false) String type) {

        List<Vehicle> vehicles = vehicleDao.findAllVehicles();
        return vehicles.stream()
                .filter(v -> vin == null || Objects.equals(v.getVin(), vin))
                .filter(v -> minPrice == null || v.getPrice() >= minPrice)
                .filter(v -> maxPrice == null || v.getPrice() <= maxPrice)
                .filter(v -> make == null || (v.getMake() != null && v.getMake().equalsIgnoreCase(make)))
                .filter(v -> model == null || (v.getModel() != null && v.getModel().equalsIgnoreCase(model)))
                .filter(v -> minYear == null || v.getYear() >= minYear)
                .filter(v -> maxYear == null || v.getYear() <= maxYear)
                .filter(v -> color == null || (v.getColor() != null && v.getColor().equalsIgnoreCase(color)))
                .filter(v -> minMiles == null || v.getOdometer() >= minMiles)
                .filter(v -> maxMiles == null || v.getOdometer() <= maxMiles)
                .filter(v -> type == null || (v.getType() != null && v.getType().equalsIgnoreCase(type)))
                .collect(Collectors.toList());
    }
}