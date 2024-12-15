package com.pluralsight.cardealership.controller;

import com.pluralsight.cardealership.dao.SalesContractDao;
import com.pluralsight.cardealership.model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/*
Entry point for all salesContract related operations annotated with @RestController so that Spring Boot knows to
create an instance of this class and map it to the appropriate URL path. The @RequestMapping annotation specifies
that this controller will handle all requests that start with /sales.
 */
@RestController
@RequestMapping(path= "/sales")
public class SalesContractController {
    private final SalesContractDao salesContractDao;

    /*
    SalesContractDao is injected into the constructor using the @Autowired annotation. This is a form of dependency
    injection that allows Spring Boot to automatically create an instance of SalesContractDao and pass it to the
    constructor when creating an instance of SalesContractController.
     */
    @Autowired
    public SalesContractController(SalesContractDao salesContractDao) {
        this.salesContractDao = salesContractDao;
    }

    /*
   The methods below are CRUD operations for vehicles. Each method is annotated with the appropriate HTTP method
   Starting with CREATE or POST, READ or GET, UPDATE or PUT, and DELETE.
    */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<SalesContract> findById(@RequestParam(required = false) Integer id) {
        List<SalesContract> salesContracts = salesContractDao.findAllSalesContracts();
        return salesContracts.stream()
                .filter(sc -> id == null || id.equals(sc.getContractID()))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SalesContract addSalesContract(@RequestBody SalesContract salesContract) {
        salesContractDao.addSalesContract(salesContract);
        return salesContract;
    }

    @PutMapping(path = "/update/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public SalesContract updateSalesContract(@PathVariable("id") Integer oldId, @RequestBody SalesContract updatedContract) {
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
    public void deleteSalesContract(@PathVariable("id") Integer id) {
        salesContractDao.deleteSalesContract(id);
    }
}