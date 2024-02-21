<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration and Login Form</title>
    <style>
        .container {
            width: 50%;
            margin: auto;
            text-align: center;
        }
        .success-message {
            color: green;
        }
        .error-message {
            color: red;
        }
        #login-btn {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            text-decoration: none;
            font-size: 16px;
            cursor: pointer;
        }
        #loggedin-container {
            display: none;
        }
    </style>
</head>
<body>

<%
    // Process the form submission
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String name = request.getParameter("Name");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String password = request.getParameter("psw");
        String confirmPassword = request.getParameter("psw-repeat");

        // Assuming a simple validation for password matching
        if (password.equals(confirmPassword)) {
%>
            <div class="container" id="loggedin-container">
                <h1 class="success-message">Thank you, <%= name %>! You are logged in.</h1>
                <img src="path/to/your/image.jpg" alt="Profile Image">
            </div>
<%
        } else {
%>
            <div class="container">
                <p class="error-message">Passwords do not match. Please try again.</p>
            </div>
<%
        }
    }
%>

<form action="<%= request.getContextPath() %>/main" method="post">
    <div class="container">
        <h1>New user Register for Facebook Lite</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>

        <label for="Name"><b>Enter Name</b></label>
        <input type="text" placeholder="Enter Full Name" name="Name" id="Name" required>
        <br>

        <label for="mobile"><b>Enter mobile</b></label>
        <input type="text" placeholder="Enter mobile number" name="mobile" id="mobile" required>
        <br>

        <label for="email"><b>Enter Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" id="email" required>
        <br>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" id="psw" required>
        <br>

        <label for="psw-repeat"><b>Repeat Password</b></label>
        <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required>
        <hr>
        <br>
        <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>
        <button type="submit" class="registerbtn">Register</button>
    </div>
</form>

<div class="container">
    <a href="#" id="login-btn">Log In</a>
</div>

<script>
    // JavaScript to handle showing the logged-in state
    document.getElementById("login-btn").onclick = function() {
        document.getElementById("loggedin-container").style.display = "block";
    };
</script>

</body>
</html>
