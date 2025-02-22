package com.example.servlet;

import com.example.dao.MongoDBConnection;
import com.example.dao.MySQLConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(HelloServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Test MySQL Connection
        String mysqlStatus = MySQLConnection.checkConnectionStatus();
        response.getWriter().println(mysqlStatus);
        logger.info(mysqlStatus);

        // Test MongoDB Connection
        String mongoStatus = MongoDBConnection.checkConnectionStatus();
        response.getWriter().println(mongoStatus);
        logger.info(mongoStatus);
    }
}