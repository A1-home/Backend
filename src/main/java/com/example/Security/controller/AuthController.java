package com.example.Security.controller;

import com.example.Security.entity.Users;
import com.example.Security.repository.UsersRepository;
import com.example.Security.service.AuthService;
import com.example.Security.service.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, UsersRepository usersRepository, JwtUtil jwtUtil) {
        this.authService = authService;
        this.usersRepository = usersRepository;
        this.jwtUtil = jwtUtil;
    }







    @PostMapping("/UserLogin")
    public ResponseEntity<?> Userlogin(@RequestBody Map<String, String> request) {
        try {

//            System.out.println(request.get("email")+" "+ request.get("password"));
            // Call the UsersLogin method and directly return its ResponseEntity
            return authService.UsersLogin(request.get("email"), request.get("password"));
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            // Handle authentication errors
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        }
    }




//    @PostMapping("/AccountLogin")
//    public ResponseEntity<?> Accountlogin(@RequestBody Map<String, String> request) {
//        String token = authService.loginAccount(request.get("email"), request.get("password"));
//        return ResponseEntity.ok(Map.of("token", token));
//    }

    @PostMapping("/UserLogout")
    public String DestroyToken (@PathVariable("token") String token)
    {
        return authService.destroyToken(token);
    }


    @PostMapping("/google-login")
    public ResponseEntity<?> verifyGoogleToken(@RequestBody TokenRequest request) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(request.getToken());
            String email = decodedToken.getEmail();

            Users user = usersRepository.findByUserEmail(email);

            if (user != null) {
                String token = jwtUtil.generateToken2(user);
                return ResponseEntity.ok(new AuthResponse(token, user));
            } else {
                return ResponseEntity.status(404).body(new AuthResponse(null, null));
            }
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body(new AuthResponse(null, null));
        }
    }


}

class TokenRequest {
    private String token;
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}


 class AuthResponse {
    private String token;
    private Users user;

    public AuthResponse(String token, Users user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}


