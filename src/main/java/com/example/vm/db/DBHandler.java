package com.example.vm.db;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Harriet on 9/20/2019.
 */
public class DBHandler {
    @Resource(lookup = "java:/vmDS")
    DataSource dataSource;
    @Produces
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();

    }
}
