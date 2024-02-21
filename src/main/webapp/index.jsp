<!-- index.jsp -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration and Login Form</title>
</head>
<body>

<%
    // Process the registration or login form submission
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String action = request.getParameter("action");

        if ("register".equalsIgnoreCase(action)) {
            // Process registration form data
            String name = request.getParameter("Name");
            String mobile = request.getParameter("mobile");
            String email = request.getParameter("email");
            String password = request.getParameter("psw");
            String confirmPassword = request.getParameter("psw-repeat");

            // Assuming a simple validation for password matching
            if (password.equals(confirmPassword)) {
                // Display a message indicating successful registration
%>
                <div class="container">
                    <h1>Thank you, <%= name %>! Happy Learning</h1>
                </div>
<%
            } else {
                // Display a message indicating password mismatch
%>
                <div class="container">
                    <p>Passwords do not match. Please try again.</p>
                </div>
<%
            }
        } else if ("login".equalsIgnoreCase(action)) {
            // Process login form data
            String email = request.getParameter("email");
            String password = request.getParameter("psw");

            // Display a message indicating successful login (handled in MainServlet.java)
        }
    }
%>

<!-- Registration Form -->
<form action="<%= request.getContextPath() %>/main" method="post">
    <div class="container">
        <h1>New user Register for Facebook Lite</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>

        <!-- ... (existing registration form fields) ... -->

        <input type="hidden" name="action" value="register">

        <button type="submit" class="registerbtn">Register</button>
    </div>
</form>

<!-- Login Form -->
<form action="<%= request.getContextPath() %>/main" method="post">
    <div class="container">
        <h1>Login to Facebook Lite</h1>
        <p>Please enter your credentials to log in.</p>
        <hr>

        <!-- ... (existing login form fields) ... -->

        <input type="hidden" name="action" value="login">

        <button type="submit" class="loginbtn">Login</button>
    </div>
</form>

<div class="container signin">
    <p>Already have an account? <a href="#">Sign in</a>.</p>
</div>

</body>
</html>
