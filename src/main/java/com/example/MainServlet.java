// MainServlet.java

package com.example;

import java.io.IOException;
import java.io.PrintWriter;
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

import org.mindrot.jbcrypt.BCrypt;

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
                    // Process registration
                    String name = request.getParameter("Name");
                    String mobile = request.getParameter("mobile");
                    String email = request.getParameter("email");
                    String password = request.getParameter("psw");
                    String confirmPassword = request.getParameter("psw-repeat");

                    // Simple validation for password matching
                    if (name != null && mobile != null && email != null && password != null && password.equals(confirmPassword)) {
                        // Hash the password before storing it
                        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                        // SQL query to insert data into the 'web' table
                        String sql = "INSERT INTO web (name, mobile, email, password) VALUES (?, ?, ?, ?)";

                        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                            preparedStatement.setString(1, name);
                            preparedStatement.setString(2, mobile);
                            preparedStatement.setString(3, email);
                            preparedStatement.setString(4, hashedPassword);

                            // Execute the query
                            int rowsAffected = preparedStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                out.println("User registered successfully!");
                            } else {
                                out.println("Failed to register user.");
                            }
                        }
                    } else {
                        out.println("Error: Invalid registration data.");
                    }
                } else if ("login".equalsIgnoreCase(action)) {
                    // Process login
                    String email = request.getParameter("email");
                    String password = request.getParameter("psw");

                    // SQL query to check if the user exists and verify the password
                    String sql = "SELECT * FROM web WHERE email = ?";
                    
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, email);

                        // Execute the query
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            // Verify the password using BCrypt
                            String hashedPassword = resultSet.getString("password");

                            if (BCrypt.checkpw(password, hashedPassword)) {
                                // User exists, set session attribute to indicate login
                                HttpSession session = request.getSession();
                                session.setAttribute("user", email);

                                // Display a message indicating successful login
                                out.println("You are logged in!");
                            } else {
                                out.println("Invalid email or password.");
                            }
                        } else {
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
}
