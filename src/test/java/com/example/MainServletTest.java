import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MainServletTest {

    private MainServlet mainServlet;

    @BeforeEach
    void setUp() {
        mainServlet = new MainServlet();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoPost_SuccessfulRegistration() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        when(request.getParameter("Name")).thenReturn("John Doe");
        when(request.getParameter("mobile")).thenReturn("1234567890");
        when(request.getParameter("email")).thenReturn("john.doe@example.com");
        when(request.getParameter("psw")).thenReturn("password");

        mainServlet.doPost(request, response);

        writer.flush();

        String result = stringWriter.toString().trim();
        assertEquals("User registered successfully!", result);
    }

    @Test
    void testDoPost_EmailAlreadyRegistered() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        when(request.getParameter("Name")).thenReturn("John Doe");
        when(request.getParameter("mobile")).thenReturn("1234567890");
        when(request.getParameter("email")).thenReturn("existing.email@example.com");
        when(request.getParameter("psw")).thenReturn("password");

        // Mock isEmailRegistered to return true
        when(mainServlet.isEmailRegistered(any(Connection.class), eq("existing.email@example.com"))).thenReturn(true);

        mainServlet.doPost(request, response);

        writer.flush();

        String result = stringWriter.toString().trim();
        assertEquals("Email already registered. Please choose another email.", result);
    }

    // Add more test cases as needed
}

class LoginServletTest {
    // You can write test cases for LoginServlet similarly
}
