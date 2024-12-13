package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.model.Dealership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class DealershipDaoImpl implements DealershipDao{
    private final JdbcTemplate mySqlDatabase;

    @Autowired
    public DealershipDaoImpl(JdbcTemplate mySqlDatabase) {
        this.mySqlDatabase = mySqlDatabase;
    }


    @Override
    public List<Dealership> findAllDealerships() {
        List<Dealership> dealerships = new ArrayList<>();
        String query = "SELECT * FROM Dealerships";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection()){
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Dealership dealership = new Dealership(
                resultSet.getInt("DealershipID"),
                resultSet.getString("Name"),
                resultSet.getString("Address"),
                resultSet.getString("Phone")
                );
                dealerships.add(dealership);


            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return dealerships;
    }
}

