package com.pluralsight.cardealership.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {
    private static BasicDataSource dataSource; //object to hold the information required to make a connection to the database

    /*
    This method returns an instance of BasicDataSource.
    If no instance exist, a new instance is initialized with the provided credentials.
     */

    //TODO ADD ERROR HANDLING
    //TODO ADD THREAD SAFETY?
    @Bean
    public BasicDataSource getDataSource(@Value("${spring.datasource.url}") String url,
                                         @Value("${spring.datasource.username}") String usr,
                                         @Value("${spring.datasource.password}") String password) {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(usr);
            dataSource.setPassword(password);
        }
        return dataSource;
    }

    /*
    This method returns a JDBCTemplate which injects the BasicDataSource instance instead of directly calling the
    getConnection method. We are able to do this because the BasicDataSource instance is a bean and is managed by spring.
    JdbcTemplate is a spring class that manages the connection to the database. I can use this class like a getConnection
    method to get a connection to the database.
     */
    @Bean
    public JdbcTemplate getConnection(BasicDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

