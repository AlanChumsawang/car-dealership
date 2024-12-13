package com.pluralsight.cardealership.config.test;

import com.pluralsight.cardealership.config.DatabaseConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConfigTest {

    /**
     * Test that the getDataSource method returns a BasicDataSource and that the environmental variables are correctly being passed.
     */
    @Test
    void testGetDataSource() {
        BasicDataSource dataSource = new DatabaseConfig().getDataSource("DB_URL", "DB_USR", "DB_PASS");
        assertNotNull(dataSource, "DataSource should not be null");
        assertEquals(System.getenv("DB_URL"), dataSource.getUrl(), "URL should match the environment variable");
        assertEquals(System.getenv("DB_USR"), dataSource.getUsername(), "Username should match the environment variable");
        assertEquals(System.getenv("DB_PASS"), dataSource.getPassword(), "Password should match the environment variable");
    }

    /**
     * Tests if the getConnection method returns a Connection and ensures that the connection is not closed.
     */
    @Test
    void testGetConnection() {
        try (Connection connection = new DatabaseConfig().getDataSource("DB_URL", "DB_USR", "DB_PASS").getConnection()) {
            assertNotNull(connection, "Connection should not be null");
            assertFalse(connection.isClosed(), "Connection should be open");
        } catch (SQLException e) {
            fail("Connection returned an Exception: " + e.getMessage());
        }
    }

    /**
     * This method tests if we are able to run a query in our database, verifying that we have established a connection
     * to an existing database.
     */
    @Test
    void testDatabaseConnection() {
        try (Connection connection = new DatabaseConfig().getDataSource("DB_URL", "DB_USR", "DB_PASS").getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {
            assertTrue(resultSet.next(), "ResultSet should have at least one row");
            assertEquals(1, resultSet.getInt(1), "Query should return 1");
        } catch (SQLException e) {
            fail("Query execution unsuccessful: " + e.getMessage());
        }
    }
}