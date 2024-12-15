package com.pluralsight.cardealership.controller;

import com.pluralsight.cardealership.dao.LeaseContractDao;
import com.pluralsight.cardealership.model.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/*
Entry point for all LeaseContract related operations annotated with @RestController so that Spring Boot knows to
create an instance of this class and map it to the appropriate URL path. The @RequestMapping annotation specifies
that this controller will handle all requests that start with /leases.
 */
@RestController
@RequestMapping(path = "/leases")
public class LeaseContractController {
    private final LeaseContractDao leaseContractDao;

    /*
  LeaseContractDao is injected into the constructor using the @Autowired annotation. This is a form of dependency
   injection that allows Spring Boot to automatically create an instance of LeaseContractDao and pass it to the
   constructor when creating an instance of LeaseContractController.
    */
    @Autowired
    public LeaseContractController(LeaseContractDao leaseContractDao) {
        this.leaseContractDao = leaseContractDao;
    }

    /*
   The methods below are CRUD operations for vehicles. Each method is annotated with the appropriate HTTP method
   Starting with CREATE or POST, READ or GET, UPDATE or PUT, and DELETE.
    */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<LeaseContract> findById(@RequestParam(required = false) Integer id) {
        List<LeaseContract> leaseContracts = leaseContractDao.findAllLeaseContracts();
        return leaseContracts.stream()
                .filter(lc -> id == null || id.equals(lc.getContractID()))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public LeaseContract addLeaseContract(@RequestBody LeaseContract leaseContract) {
        leaseContractDao.addLeaseContract(leaseContract);
        return leaseContract;
    }

    @PutMapping(path = "/update/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public LeaseContract updateLeaseContract(@PathVariable("id") Integer oldId, @RequestBody LeaseContract updatedContract) {
        LeaseContract existingLeaseContract = leaseContractDao.findLeaseContractById(oldId);
        if (existingLeaseContract != null) {
            leaseContractDao.updateLeaseContract(updatedContract);
            return updatedContract;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lease contract not found");
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteLeaseContract(@PathVariable("id") Integer id) {
        leaseContractDao.deleteLeaseContract(id);
    }
}