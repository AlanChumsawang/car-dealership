package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.model.SalesContract;
import com.pluralsight.cardealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class SalesContractImpl implements SalesContractDao {
    private final JdbcTemplate mySqlDatabase;

    @Autowired
    public SalesContractImpl(JdbcTemplate mySqlDatabase) {
        this.mySqlDatabase = mySqlDatabase;
    }

    @Override
    public List<SalesContract> findAllSalesContracts() {
        List<SalesContract> salesContracts = new ArrayList<>();
        String query = "SELECT * FROM Sales_Contracts sc JOIN Vehicles v ON sc.VIN = v.VIN";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                salesContracts.add(createSalesContractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return salesContracts;
    }

    @Override
    public SalesContract findSalesContractById(int id) {
        SalesContract salesContract = null;
        String query = "SELECT * FROM Sales_Contracts sc JOIN Vehicles v ON sc.VIN = v.VIN WHERE sc.Contract_ID = ?";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                salesContract = createSalesContractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return salesContract;
    }

    @Override
    public void addSalesContract(SalesContract salesContract) {
        String query = "INSERT INTO Sales_Contracts (Contract_ID, VIN, customerId, Sale_Date, Total_Price, isFinanced, Loan_Term, customerName, customerEmail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection())
        {
             PreparedStatement preparedStatement = connection.prepareStatement(query);

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
            throw new RuntimeException();
        }
    }

    @Override
    public void updateSalesContract(SalesContract salesContract) {
        String query = "UPDATE Sales_Contracts SET VIN = ?, customerId = ?, Sale_Date = ?, Total_Price = ?, isFinanced = ?, Loan_Term = ?, customerName = ?, customerEmail = ? WHERE Contract_ID = ?";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteSalesContract(int id) {
        String query = "DELETE FROM Sales_Contracts WHERE Contract_ID = ?";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
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