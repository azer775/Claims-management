package org.example.gestionsinistre.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.Registration;
import jakarta.validation.Valid;
import org.example.gestionsinistre.entities.AuthentificationRequest;
import org.example.gestionsinistre.entities.AuthentificationResponse;
import org.example.gestionsinistre.entities.RegistrationRequest;
import org.example.gestionsinistre.entities.User;
import org.example.gestionsinistre.repositories.UserRepository;
import org.example.gestionsinistre.services.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin("*")
@RequestMapping("auth")
public class AuthentificationController {
    @Autowired
    AuthentificationService as;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/register")
    //@ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        as.register(request);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthentificationResponse> authenticate(@RequestBody @Valid AuthentificationRequest authentificationRequest){
        User user = userRepository.findByEmail(authentificationRequest.getEmail());
        if (user==null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new AuthentificationResponse("Email not found"));
        }
        if (!user.isEnabled()){
            return ResponseEntity
                    .status(HttpStatus.LOCKED)
                    .body(new AuthentificationResponse("Account not activated"));
        }
        return ResponseEntity.ok(as.authenticate(authentificationRequest));
    }
    @GetMapping("/activate-account")
    public boolean confirm(@RequestParam String token) throws MessagingException {
       return as.activateAccount(token);
    }
    @GetMapping("current")
    public User connectedUser(String token){

        return this.as.getcurrentuser(token);
    }
    @GetMapping("userc")
    public User connctedUser(@RequestHeader(value = "Authorization",required = false) String auth){
        String jwtToken = auth.replace("Bearer ", "");
        return this.as.getcurrentuser(jwtToken);
        //return this.as.getuser(auth);
    }
    @GetMapping("all")
    public List<User> getall(){
        return userRepository.findAll();
    }
    @GetMapping("role")
    public Map<String, String> getRole(@RequestHeader(value = "Authorization",required = false) String auth){
        Map<String, String> response = new HashMap<>();
        response.put("role", this.as.getRole(auth));
        return response;
    }
}
