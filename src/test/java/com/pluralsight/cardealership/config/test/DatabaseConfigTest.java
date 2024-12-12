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
     * Test that the getDataSource method returns a BasicDataSource and that the environmental variables are correctly being passed
     */
    @Test
    void testGetDataConfigTest() {
        BasicDataSource dataSource = DatabaseConfig.getDataSource("DB_URL", "DB_USR", "DB_PASS");
        assertNotNull(dataSource);
        assertEquals(System.getenv("DB_URL"), dataSource.getUrl());
        assertEquals(System.getenv("DB_USR"), dataSource.getUsername());
        assertEquals(System.getenv("DB_PASS"), dataSource.getPassword());
    }

    /**
     * Tests if the getConnection method returns a Connection and ensures that the connection is not closed.
     */

    @Test
    void testGetConnection() {
        try (Connection connection = DatabaseConfig.getConnection("DB_URL", "DB_USR", "DB_PASS")) {
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            fail("Connection returned an Exception");
        }
    }

    /**
     * This method test if we are able to run a query in our database, verifying that we have established a connection
     * to an existing database
     */
    @Test
    void testDatabaseConnection() {
        try (Connection connection = DatabaseConfig.getConnection("DB_URL", "DB_USR", "DB_PASS");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {
            assertTrue(resultSet.next());
            assertEquals(1, resultSet.getInt(1));
        } catch (SQLException e) {
            fail("Query execution unsuccessful");
        }
    }
}