package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.config.DatabaseConfig;
import com.pluralsight.cardealership.model.Dealership;
import com.pluralsight.cardealership.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealershipDaoImpl implements DealershipDao{
    private final String url;
    private final String usr;
    private final String password;

    public DealershipDaoImpl(String url, String usr, String password) {
        this.url = url;
        this.usr = usr;
        this.password = password;
    }


    @Override
    public List<Dealership> findAllDealerships() {
        List<Dealership> dealerships = new ArrayList<>();
        String query = "SELECT * FROM Dealerships";

        try (Connection connection = DatabaseConfig.getConnection(url,usr,password);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

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
            e.printStackTrace();
        }
        return dealerships;
    }
}

