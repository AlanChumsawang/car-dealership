package com.pluralsight.cardealership.controller;

import com.pluralsight.cardealership.dao.SalesContractDao;
import com.pluralsight.cardealership.model.SalesContract;
import com.pluralsight.cardealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path= "/sales")
public class SalesContractController {
    private final SalesContractDao salesContractDao;

    @Autowired
    public SalesContractController(SalesContractDao salesContractDao) {
        this.salesContractDao = salesContractDao;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<SalesContract> findAll() {
        return salesContractDao.findAllSalesContracts();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SalesContract addSalesContract(@RequestBody SalesContract salesContract) {
        salesContractDao.addSalesContract(salesContract);
        return salesContract;
    }

    @PutMapping(path = "/update/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public SalesContract updateSalesContract(@PathVariable("vin") Integer oldId, @RequestBody SalesContract updatedContract) {
        SalesContract existingSalesContract = salesContractDao.findSalesContractById(oldId);
        if (existingSalesContract != null) {
            salesContractDao.updateSalesContract(updatedContract);
            return updatedContract;

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
    }


    @DeleteMapping(path = "/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteSalesContract(@PathVariable("vin") Integer id) {
        salesContractDao.deleteSalesContract(id);
    }
}
