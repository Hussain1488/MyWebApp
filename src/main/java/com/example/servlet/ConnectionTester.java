package com.example.servlet;

import com.example.dao.MongoDBConnection;
import com.example.dao.MySQLConnection;

import java.util.logging.Logger;

public class ConnectionTester {
    private static final Logger logger = Logger.getLogger(ConnectionTester.class.getName());

    public void testConnections() {
        // Test MySQL Connection
        String mysqlStatus = MySQLConnection.checkConnectionStatus();
        System.out.println(mysqlStatus); // Print to terminal
//        logger.info(mysqlStatus);

        // Test MongoDB Connection
        String mongoStatus = MongoDBConnection.checkConnectionStatus();
        System.out.println(mongoStatus); // Print to terminal
//        logger.info(mongoStatus);
    }
}