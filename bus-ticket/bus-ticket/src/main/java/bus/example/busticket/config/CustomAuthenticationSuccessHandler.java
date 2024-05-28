//package bus.example.busticket.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//
//import java.io.IOException;
//import java.util.Collection;
//
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//        @Override
//        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//            throws IOException, ServletException {
//
//            // Logic to handle successful authentication (e.g., set a success response code and message)
//            response.setStatus(HttpStatus.OK.value()); // Set 200 OK status code
//            response.getWriter().write("Login successful!"); // Example message
//
//            // Optionally, send a JWT token or other relevant data

            // Prevent further processing by Spring Security
//            return;
//        }
//    }

