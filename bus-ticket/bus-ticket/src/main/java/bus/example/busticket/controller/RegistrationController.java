package bus.example.busticket.controller;

import bus.example.busticket.dto.RegisteredUserDTO;
import bus.example.busticket.service.DefaultUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/Registration")
public class RegistrationController {

    private DefaultUserService defaultUserService;

//    @ModelAttribute
//    public RegisteredUserDTO registeredUserDTO()
//    {
//        return new RegisteredUserDTO();
//    }

    public RegistrationController(DefaultUserService defaultUserService) {
        super();
        this.defaultUserService = defaultUserService;
    }

//    @GetMapping
//    public String register()
//    {
//        return "/api/Registration";
//    }





    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody RegisteredUserDTO registeredUserDTO)
    {
        try {
            defaultUserService.save(registeredUserDTO);
            return ResponseEntity.ok().build(); // Registration successful
        } catch (Exception e) {
            // Handle registration exceptions appropriately (e.g., user already exists, invalid data)
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }

    }
}
