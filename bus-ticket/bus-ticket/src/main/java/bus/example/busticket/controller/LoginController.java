package bus.example.busticket.controller;

import bus.example.busticket.config.JwtProvider;
import bus.example.busticket.dto.UserLoginDTO;
import bus.example.busticket.response.AuthResponse;
import bus.example.busticket.service.DefaultUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/api")
public class LoginController {


    private DefaultUserService defaultUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public LoginController(DefaultUserService defaultUserService) {
        super();
        this.defaultUserService = defaultUserService;
    }
    private SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();


    //To provide a specific type of object to the model before any form rendering happens
    //It returns an instance of UserLoginDTO, which is an empty object that the form will use to bind input data when
    //the user will enter the details
    //So basically this functions prepares an object to hold form data and makes it
    //available before the form is displayed
//    @ModelAttribute
//    public UserLoginDTO userLoginDTO(){
//        return new UserLoginDTO();
//    }

//    //Displays the login form to the user
//    @GetMapping
//    public String login()
//    {
//        return "/api/Login";
//    }

    //It is important for this function to return something otherwise spring-security
    //returns the default html sign in page
    @PostMapping("/Login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response) {

        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        System.out.println(username+"-------"+password);

        Authentication authentication = authenticate(username,password);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }
    private Authentication authenticate(String username, String password) {

        System.out.println(username+"---++----"+password);

        UserDetails userDetails = defaultUserService.loadUserByUsername(username);

        System.out.println("Sign in user details"+ userDetails);

        if(userDetails == null) {
            System.out.println("Sign in details - null" + userDetails);

            throw new BadCredentialsException("Invalid username and password");
        }
        System.out.println(passwordEncoder.encode(password));
        System.out.println(userDetails.getPassword());
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch"+userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

}






