package com.example.Security.service;

import com.example.Security.entity.Account;
import com.example.Security.entity.Users;
import com.example.Security.repository.AccountRepository;
import com.example.Security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {


    private final UsersRepository usersRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsersRepository usersRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RestTemplate restTemplate) {

        this.usersRepository = usersRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplate;
    }






//    public ResponseEntity<Map<String, Object>> UsersLogin(String email, String password) {
//        // Find the user by email
//        Users user = usersRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("No user found"));
//
//        // Verify the password
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Invalid email or password");
//        }
//
//        // Generate the JWT token
//        String token = jwtUtil.generateToken(email);
//
//        // Prepare the response map
//        Map<String, Object> response = new HashMap<>();
//        response.put("token", token);
//        response.put("user", user);
//        System.out.println(user);
//
//        // Return the response with HTTP 200 status
//        return ResponseEntity.ok(response);
//    }

    @Autowired
    private final RestTemplate restTemplate;

    public ResponseEntity<Map<String, Object>> UsersLogin(String email, String password) {
        // Find the user by email

//        System.out.println(email +" "+ password);
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));

        // Verify the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        // Generate the JWT token
        String token = jwtUtil.generateToken(user);


        // Prepare the response map
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }



//    public String loginAccount(String email, String password) {
//       Account account=accountRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
//        if (password.equals(account.getPassword())) {
//            return jwtUtil.generateToken(email);
//
//        }
//        throw new BadCredentialsException("Invalid email or password");
//
//    }

    public String EncodeUsersPassword(String password)
    {
        return passwordEncoder.encode(password);

    }


    private Map<String, Boolean> tokenStore = new HashMap<>();

    // Method to destroy the token
    public String destroyToken(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token); // Remove token from the store
            return "Token destroyed successfully.";
        } else {
            return "Token not found or already invalid.";
        }
    }
}

