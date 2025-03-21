package com.example.servlet;

import com.example.dao.MySQLConnection;

import java.util.logging.Logger;

public class ConnectionTester {
    private static final Logger logger = Logger.getLogger(ConnectionTester.class.getName());



    public void testConnections() {
        // Test MySQL Connection
        System.out.println("\n");
        MySQLConnection.checkConnectionStatus();
        System.out.println("\n");
    }

}