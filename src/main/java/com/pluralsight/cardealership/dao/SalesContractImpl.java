package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.config.DatabaseConfig;
import com.pluralsight.cardealership.model.SalesContract;
import com.pluralsight.cardealership.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesContractImpl implements SalesContractDao {
    private final String url;
    private final String usr;
    private final String password;

    public SalesContractImpl(String url, String usr, String password) {
        this.url = url;
        this.usr = usr;
        this.password = password;
    }

    @Override
    public List<SalesContract> findAllSalesContracts() {
        List<SalesContract> salesContracts = new ArrayList<>();
        String query = "SELECT * FROM Sales_Contracts sc JOIN Vehicles v ON sc.VIN = v.VIN";

        try (Connection connection = DatabaseConfig.getConnection(url, usr, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                salesContracts.add(createSalesContractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesContracts;
    }

    @Override
    public SalesContract findSalesContractById(int id) {
        SalesContract salesContract = null;
        String query = "SELECT * FROM Sales_Contracts sc JOIN Vehicles v ON sc.VIN = v.VIN WHERE sc.Contract_ID = ?";

        try (Connection connection = DatabaseConfig.getConnection(url, usr, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                salesContract = createSalesContractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesContract;
    }

    @Override
    public void addSalesContract(SalesContract salesContract) {
        String query = "INSERT INTO Sales_Contracts (Contract_ID, VIN, customerId, Sale_Date, Total_Price, isFinanced, Loan_Term, customerName, customerEmail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection(url, usr, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, salesContract.getContractID());
            preparedStatement.setInt(2, salesContract.getVehicle().getVin());
            preparedStatement.setInt(3, salesContract.getCustomerId());
            preparedStatement.setString(4,salesContract.getStartDate());
            preparedStatement.setDouble(5, salesContract.getTotalPrice());
            preparedStatement.setBoolean(6, salesContract.isFinanced());
            preparedStatement.setInt(7, salesContract.getLoanTerm());
            preparedStatement.setString(8, salesContract.getCustomerName());
            preparedStatement.setString(9, salesContract.getCustomerEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSalesContract(SalesContract salesContract) {
        String query = "UPDATE Sales_Contracts SET VIN = ?, customerId = ?, Sale_Date = ?, Total_Price = ?, isFinanced = ?, Loan_Term = ?, customerName = ?, customerEmail = ? WHERE Contract_ID = ?";

        try (Connection connection = DatabaseConfig.getConnection(url, usr, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, salesContract.getVehicle().getVin());
            preparedStatement.setInt(2, salesContract.getCustomerId());
            preparedStatement.setDate(3, Date.valueOf(salesContract.getStartDate()));
            preparedStatement.setDouble(4, salesContract.getTotalPrice());
            preparedStatement.setBoolean(5, salesContract.isFinanced());
            preparedStatement.setInt(6, salesContract.getLoanTerm());
            preparedStatement.setString(7, salesContract.getCustomerName());
            preparedStatement.setString(8, salesContract.getCustomerEmail());
            preparedStatement.setInt(9, salesContract.getContractID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSalesContract(int id) {
        String query = "DELETE FROM Sales_Contracts WHERE Contract_ID = ?";

        try (Connection connection = DatabaseConfig.getConnection(url, usr, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SalesContract createSalesContractFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle(
                resultSet.getInt("VIN"),
                resultSet.getInt("Year"),
                resultSet.getString("Make"),
                resultSet.getString("Model"),
                resultSet.getString("Type"),
                resultSet.getString("Color"),
                resultSet.getInt("Odometer"),
                resultSet.getDouble("Price"),
                resultSet.getBoolean("Sold")
        );

        return new SalesContract(
                resultSet.getInt("Contract_ID"),
                resultSet.getString("Sale_Date"),
                resultSet.getString("customerName"),
                resultSet.getString("customerEmail"),
                resultSet.getInt("customerId"),
                vehicle,
                resultSet.getBoolean("isFinanced")
        );
    }
}