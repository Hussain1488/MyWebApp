package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/auth/*") // Handles all routes under "/auth/"
public class AuthController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // Get the subpath (e.g., "/login", "/register")

        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid authentication request.");
            return;
        }


        switch (pathInfo) {
            case "/login":
//                System.out.println("doGet called with pathInfo: " + pathInfo);
                request.getRequestDispatcher("/pages/auth/login.jsp").forward(request, response); // Forward to login.jsp
                break;
            case "/register":
                request.getRequestDispatcher("/pages/auth/register.jsp").forward(request, response); // Forward to register.jsp
                break;
            case "/logout":
                request.getRequestDispatcher("/pages/auth/login.jsp").forward(request, response); // Forward to login.jsp
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Route not found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // Get the subpath (e.g., "/login", "/register")

        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid authentication request.");
            return;
        }

        System.out.println("doPost called with pathInfo: " + pathInfo);
        switch (pathInfo) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/register":
                handleRegister(request, response);
                break;
            case "/logout":
                handleLogout(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Route not found.");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Example authentication logic
        if ("admin@example.com".equals(email) && "password".equals(password)) {
            request.getSession().setAttribute("user", email);
            response.getWriter().println("Login successful!");
        } else {
            response.getWriter().println("Invalid email or password.");
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // TODO: Store user credentials in the database
        response.getWriter().println("User registered successfully!");
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.getSession().invalidate(); // Destroy session
        response.getWriter().println("Logout successful!");
    }
}