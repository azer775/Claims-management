package org.example.gestionsinistre.services;

import jakarta.mail.MessagingException;

import jakarta.transaction.Transactional;
import org.example.gestionsinistre.entities.*;
import org.example.gestionsinistre.repositories.RoleRepository;
import org.example.gestionsinistre.repositories.TokenRepository;
import org.example.gestionsinistre.repositories.UserRepository;
import org.example.gestionsinistre.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthentificationService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    MailingService mailingService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

    public void register(RegistrationRequest request) throws MessagingException {
        Role userRole = roleRepository.findByNom("client");
        User user= User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .pwd(passwordEncoder.encode(request.getPwd()))
                .idun(request.getIdun())
                .datenaiss(request.getDatenais())
                .locked(false)
                .enable(false)
                .roles(List.of(userRole))
                .build();
                userRepository.save(user);
                sendValidationEmail(user);

    }
    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        mailingService.sendEmail(user,"Validation",newToken);
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationToken(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationToken(int length) {
        String chars="0123456789";
        StringBuilder codebuilder = new StringBuilder();
        SecureRandom secureRandom =new SecureRandom();
        for(int i=0;i< length;i++){
            int randomIndex = secureRandom.nextInt(chars.length());
            codebuilder.append(chars.charAt(randomIndex));
        }
        return codebuilder.toString();
    }

    public AuthentificationResponse authenticate(AuthentificationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPwd()
                )
        );

        var claims = new HashMap<String,Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("nom",user.getNom());
        var jwtToken = jwtService.generateToken(claims,user);
        return AuthentificationResponse.builder().token(jwtToken).message("Authentication successful").build();
    }
    @Transactional
    public boolean activateAccount(String token) throws MessagingException {
        Token savedToken =tokenRepository.findByToken(token);
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("new token has been sent");
        }
        User user =userRepository.findById(savedToken.getUser().getId()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        user.setEnable(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
        return true;
    }
    public User getcurrentuser( String auth){
       ;
        return this.userRepository.findByEmail( this.jwtService.extractUsername(auth));
    }
    public User getuser(Authentication auth){
        return (User) auth.getPrincipal();
    }

    public String getRole(String auth) {
            auth=auth.replace("Bearer ", "");
        return this.userRepository.findByEmail( this.jwtService.extractUsername(auth)).getRoles().getFirst().getNom();
    }
    public void test3(){
        System.out.print("");
    }
}
