package com.example.Security.controller;

import com.example.Security.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }







    @PostMapping("/UserLogin")
    public ResponseEntity<?> Userlogin(@RequestBody Map<String, String> request) {
        try {

            System.out.println(request.get("email")+" "+ request.get("password"));
            // Call the UsersLogin method and directly return its ResponseEntity
            return authService.UsersLogin(request.get("email"), request.get("password"));
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            // Handle authentication errors
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        }
    }


    @PostMapping("/AccountLogin")
    public ResponseEntity<?> Accountlogin(@RequestBody Map<String, String> request) {
        String token = authService.loginAccount(request.get("email"), request.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/UserLogout")
    public String DestroyToken (@PathVariable("token") String token)
    {
        return authService.destroyToken(token);
    }
}

