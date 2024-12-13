package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.model.SalesContract;

import java.util.List;

public interface SalesContractDao {
    List<SalesContract> findAllSalesContracts();
    SalesContract findSalesContractById(int id);
    void addSalesContract(SalesContract salesContract);
    void updateSalesContract(SalesContract salesContract);
    void deleteSalesContract(int id);
}
