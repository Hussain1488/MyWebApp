package com.example.servlet;

import com.example.dao.MySQLConnection;
import com.example.dao.MongoDBConnection;
import com.mongodb.client.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/hello") // Maps the servlet to the URL "/hello"
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Test MySQL Connection
        try {
            Connection mysqlConn = MySQLConnection.getConnection();
            response.getWriter().println("MySQL Connection Successful!");
            mysqlConn.close(); // Close the connection
        } catch (SQLException e) {
            response.getWriter().println("MySQL Error: " + e.getMessage());
        }

//         Test MongoDB Connection
        try {
            MongoClient mongoClient = MongoDBConnection.getConnection();
            response.getWriter().println("MongoDB Connection Successful!");
            mongoClient.close(); // Close the connection
        } catch (Exception e) {
            response.getWriter().println("MongoDB Error: " + e.getMessage());
        }
    }
}