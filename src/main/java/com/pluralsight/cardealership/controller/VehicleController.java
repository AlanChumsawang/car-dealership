package com.pluralsight.cardealership.controller;

import com.pluralsight.cardealership.dao.VehicleDao;
import com.pluralsight.cardealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path= "/vehicles")
public class VehicleController {
    private final VehicleDao vehicleDao;

    @Autowired
    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        vehicleDao.addVehicle(vehicle);
        return vehicle;
    }

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
}
