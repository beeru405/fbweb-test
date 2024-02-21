// MainServlet.java

package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    // Update these values with your database details
    private static final String JDBC_URL = "jdbc:mysql://192.168.138.126:3306/myDB";
    private static final String JDBC_USER = "mysql";
    private static final String JDBC_PASSWORD = "mysql";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                // Perform database operations
                String name = request.getParameter("Name");
                String mobile = request.getParameter("mobile");
                String email = request.getParameter("email");
                String password = request.getParameter("psw");

                // SQL query to insert data into the 'web1' table
                String sql = "INSERT INTO web (name, mobile, email, password) VALUES (?, ?, ?, ?)";
                
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, mobile);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, password);

                    // Execute the query
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        out.println("User registered successfully!");
                    } else {
                        out.println("Failed to register user.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}
