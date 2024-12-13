package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.model.Dealership;
import java.util.List;

public interface DealershipDao {
    List<Dealership> findAllDealerships();
}
