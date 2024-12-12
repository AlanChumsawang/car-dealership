package com.pluralsight.cardealership.config;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConfig {
    private static BasicDataSource dataSource; //object to hold the information required to make a connection to the database


    //TODO ADD ERROR HANDLING
    //ADD THREAD SAFETY?
    public static BasicDataSource getDataSource(String url, String usr, String password) {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(System.getenv(url));
            dataSource.setUsername(System.getenv(usr));
            dataSource.setPassword(System.getenv(password));
        }
        return dataSource;
    }

    public static Connection getConnection(String url, String usr, String password) throws SQLException {
        return getDataSource(url, usr, password).getConnection();
    }
}

