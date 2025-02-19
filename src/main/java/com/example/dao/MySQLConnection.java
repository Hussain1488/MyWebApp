package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/my_app";
        String username = "root";      // Replace with your MySQL username
        String password = "root";     // Replace with your MySQL password
        return DriverManager.getConnection(url, username, password);
    }
}