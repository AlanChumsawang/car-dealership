package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.model.LeaseContract;
import com.pluralsight.cardealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class LeaseContractDaoImpl implements LeaseContractDao {
    private final JdbcTemplate mySqlDatabase;

    @Autowired
    public LeaseContractDaoImpl(JdbcTemplate mySqlDatabase) {
        this.mySqlDatabase = mySqlDatabase;
    }

    @Override
    public List<LeaseContract> findAllLeaseContracts() {
        /*
        This query requires a join between the lease_contracts and Vehicles tables. The query retrieves all columns from both tables.
        which means there must be a matching vehicle in the Vehicles table for each lease contract in the lease_contracts table
         */
        List<LeaseContract> leaseContracts = new ArrayList<>();
        String query = "SELECT * FROM lease_contracts lc JOIN Vehicles v ON lc.VIN = v.VIN";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                leaseContracts.add(createLeaseContractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not retrieve lease contracts", e);
        }
        return leaseContracts;
    }

    @Override
    public LeaseContract findLeaseContractById(int id) {
        LeaseContract leaseContract = null;
        String query = "SELECT * FROM lease_contracts lc JOIN Vehicles v ON lc.VIN = v.VIN WHERE lc.contract_id = ?";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                leaseContract = createLeaseContractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return leaseContract;
    }

    @Override
    public void addLeaseContract(LeaseContract leaseContract) {
        String query = "INSERT INTO lease_contracts (contract_id, VIN, lease_start_date, total_price, lease_fee, expected_ending_value, monthly_payment, customer_name, customer_email, customer_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, leaseContract.getContractID());
            preparedStatement.setInt(2, leaseContract.getVehicle().getVin());
            preparedStatement.setString(3, leaseContract.getStartDate());
            preparedStatement.setDouble(4, leaseContract.getTotalPrice());
            preparedStatement.setDouble(5, leaseContract.getLeaseFee());
            preparedStatement.setDouble(6, leaseContract.getExpectedEndingValue(leaseContract.getVehicle()));
            preparedStatement.setDouble(7, leaseContract.getMonthlyPayment());
            preparedStatement.setString(8, leaseContract.getCustomerName());
            preparedStatement.setString(9, leaseContract.getCustomerEmail());
            preparedStatement.setInt(10, leaseContract.getCustomerId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding lease contract", e);
        }
    }

    @Override
    public void updateLeaseContract(LeaseContract leaseContract) {
        String query = "UPDATE lease_contracts SET VIN = ?, lease_start_date = ?, total_price = ?, lease_fee = ?, expected_ending_value = ?, monthly_payment = ?, customer_name = ?, customer_email = ? WHERE contract_id = ?";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, leaseContract.getVehicle().getVin());
            preparedStatement.setString(2, leaseContract.getStartDate());
            preparedStatement.setDouble(3, leaseContract.getTotalPrice());
            preparedStatement.setDouble(4, leaseContract.getLeaseFee());
            preparedStatement.setDouble(5, leaseContract.getExpectedEndingValue(leaseContract.getVehicle()));
            preparedStatement.setDouble(6, leaseContract.getMonthlyPayment());
            preparedStatement.setString(7, leaseContract.getCustomerName());
            preparedStatement.setString(8, leaseContract.getCustomerEmail());
            preparedStatement.setInt(9, leaseContract.getContractID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteLeaseContract(int id) {
        String query = "DELETE FROM lease_contracts WHERE contract_id = ?";

        try (Connection connection = Objects.requireNonNull(mySqlDatabase.getDataSource()).getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /*
        * Helper method to create a LeaseContract object from a ResultSet. Because a LeaseContract object contains
        * a Vehicle object, I had to create a Vehicle object from the ResultSet as well. This means there must be a
        * matching vehicle in the database for each lease contract.
     */
    private LeaseContract createLeaseContractFromResultSet(ResultSet resultSet) throws SQLException {
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

        LeaseContract leaseContract = new LeaseContract(
                resultSet.getInt("contract_id"),
                resultSet.getString("lease_start_date"),
                resultSet.getString("customer_name"),
                resultSet.getString("customer_email"),
                resultSet.getInt("customer_id"),
                vehicle,
                resultSet.getDouble("total_price")
        );

        /*
            * I had to set the lease fee, expected ending value, and monthly payment for the LeaseContract object.
            * These values are not stored in the database, so I had to calculate them using the LeaseContract
            * object's methods.
         */
        leaseContract.setLeaseFee(resultSet.getDouble("lease_fee"));
        leaseContract.setExpectedEndingValue(resultSet.getDouble("expected_ending_value"));
        leaseContract.setMonthlyPayment(resultSet.getDouble("monthly_payment"));

        return leaseContract;
    }
}