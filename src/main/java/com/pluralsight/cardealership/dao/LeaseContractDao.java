package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.model.LeaseContract;

import java.util.List;

public interface LeaseContractDao {
    List<LeaseContract> findAllLeaseContracts();
    LeaseContract findLeaseContractById(int id);
    void addLeaseContract(LeaseContract leaseContract);
    void updateLeaseContract(LeaseContract leaseContract);
    void deleteLeaseContract(int id);
}