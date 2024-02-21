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
            String name = request.getParameter("name");
            String mobile = request.getParameter("mobile");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            // Simple validation for password matching
            if (name != null && mobile != null && email != null && password != null && confirmPassword != null
                    && password.equals(confirmPassword)) {
                // Display a message indicating successful registration
%>
                <div class="container">
                    <h1>Thank you, <%= name %>! Happy Learning</h1>
                </div>
<%
            } else {
                // Display a message indicating validation failure
%>
                <div class="container">
                    <p>Invalid registration data. Please check your input and try again.</p>
                </div>
<%
            }
        } else if ("login".equalsIgnoreCase(action)) {
            // Process login form data
            String email = request.getParameter("email");
            String password = request.getParameter("password");

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

        <label for="name"><b>Enter Name</b></label>
        <input type="text" placeholder="Enter Full Name" name="name" id="name" required>

        <label for="mobile"><b>Enter Mobile</b></label>
        <input type="text" placeholder="Enter Mobile Number" name="mobile" id="mobile" required>

        <label for="email"><b>Enter Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" id="email" required>

        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" id="password" required>

        <label for="confirmPassword"><b>Confirm Password</b></label>
        <input type="password" placeholder="Confirm Password" name="confirmPassword" id="confirmPassword" required>

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

        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" id="email" required>

        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" id="password" required>

        <input type="hidden" name="action" value="login">

        <button type="submit" class="loginbtn">Login</button>
    </div>
</form>

<div class="container signin">
    <p>Already have an account? <a href="#">Sign in</a>.</p>
</div>

</body>
</html>
