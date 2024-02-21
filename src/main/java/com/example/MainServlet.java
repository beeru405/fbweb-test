@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://192.168.138.114:3306/myDB";
    private static final String JDBC_USER = "mysql";
    private static final String JDBC_PASSWORD = "mysql";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                String name = request.getParameter("Name");
                String mobile = request.getParameter("mobile");
                String email = request.getParameter("email");
                String password = request.getParameter("psw");

                String hashedPassword = hashPassword(password);

                String sql = "INSERT INTO web (name, mobile, email, password) VALUES (?, ?, ?, ?)";
                
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, mobile);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, hashedPassword);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        out.println("User registered successfully!");
                    } else {
                        out.println("Failed to register user.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedBytes) {
            stringBuilder.append(String.format("%02x", b));
        }

        return stringBuilder.toString();
    }
}
