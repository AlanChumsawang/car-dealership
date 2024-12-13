package com.pluralsight.cardealership.dao;

import com.pluralsight.cardealership.model.Vehicle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleDaoImpl implements VehicleDao {
    private final JdbcTemplate mySqlDatabase;

    public VehicleDaoImpl(JdbcTemplate mySqlDatabase) {
        this.mySqlDatabase = mySqlDatabase;
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM Vehicles";

        try (Connection connection = mySqlDatabase.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                vehicles.add(createVehicleFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public Vehicle findVehicleByVin(int vin) {
        String query = "SELECT * FROM Vehicles WHERE Vin = ?";

        try (Connection connection = mySqlDatabase.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, String.valueOf(vin));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createVehicleFromResultSet(resultSet);
            } else {
                throw new RuntimeException("Vehicle with VIN " + vin + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        String query = """
        INSERT INTO Vehicles (VIN, Year, Make, Model, Type, Color, Odometer, Price, Sold)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection connection = mySqlDatabase.getDataSource().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, vehicle.getVin());
            preparedStatement.setInt(2, vehicle.getYear());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setString(5, vehicle.getType());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setInt(7, vehicle.getOdometer());
            preparedStatement.setDouble(8, vehicle.getPrice());
            preparedStatement.setBoolean(9, vehicle.isSold());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVehicle(Integer vin) {
        String query = "DELETE FROM Vehicles WHERE VIN = ?";
        try (Connection connection = mySqlDatabase.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, vin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateVehicle(Vehicle vehicle, int oldVin) {
        String query = """
        UPDATE Vehicles SET VIN = ?, Year = ?, Make = ?, Model = ?, Type = ?, Color = ?, Odometer = ?, Price = ?, Sold = ?
        WHERE VIN = ?
        """;
        try (Connection connection = mySqlDatabase.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, vehicle.getVin());
            preparedStatement.setInt(2, vehicle.getYear());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setString(5, vehicle.getType());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setInt(7, vehicle.getOdometer());
            preparedStatement.setDouble(8, vehicle.getPrice());
            preparedStatement.setBoolean(9, vehicle.isSold());
            preparedStatement.setInt(10, oldVin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        return new Vehicle(
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
    }
}