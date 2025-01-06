package com.example.Security.controller;

import com.example.Security.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> request) {
        String token = authService.signup(request.get("name"), request.get("email"), request.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String token = authService.login(request.get("email"), request.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }



    @PostMapping("/UserLogin")
    public ResponseEntity<?> Userlogin(@RequestBody Map<String, String> request) {
        try {
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
}

