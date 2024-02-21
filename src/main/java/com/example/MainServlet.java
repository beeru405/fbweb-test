// MainServlet.java

package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
                // Check if it's a registration or login request
                String action = request.getParameter("action");

                if ("register".equalsIgnoreCase(action)) {
                    // Process registration (similar to your existing code)
                    // ...

                } else if ("login".equalsIgnoreCase(action)) {
                    // Process login
                    String email = request.getParameter("email");
                    String password = request.getParameter("psw");

                    // Hash the entered password for comparison
                    String hashedPassword = hashPasswordSHA256(password);

                    // SQL query to check if the user exists and credentials are correct
                    String sql = "SELECT * FROM web WHERE email = ? AND password = ?";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, email);
                        preparedStatement.setString(2, hashedPassword);

                        // Execute the query
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            // User exists, set session attribute to indicate login
                            HttpSession session = request.getSession();
                            session.setAttribute("user", email);

                            // Display a message indicating successful login
                            out.println("You are logged in!");
                        } else {
                            // Display a message indicating invalid email or password
                            out.println("Invalid email or password.");
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }

    private String hashPasswordSHA256(String password) {
        try {
            if (password != null) {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes());

                // Convert the byte array to a hexadecimal representation
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                return hexString.toString();
            } else {
                throw new IllegalArgumentException("Password cannot be null");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Error hashing password.");
        }
    }
}
