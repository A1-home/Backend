package com.example.Security.service;

import com.example.Security.entity.Account;
import com.example.Security.entity.User;
import com.example.Security.entity.Users;
import com.example.Security.repository.AccountRepository;
import com.example.Security.repository.UserRepository;
import com.example.Security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository userRepository;

    private final UsersRepository usersRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, UsersRepository usersRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.usersRepository = usersRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplate;
    }

    public String signup(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return jwtUtil.generateToken(email);
    }



    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return jwtUtil.generateToken(email);
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
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));

        // Verify the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        // Generate the JWT token
        String token = jwtUtil.generateToken(email);

        // Fetch data using the accountId
        Long accountId = user.getAccount().getAccountId();
        String baseUrl = "http://localhost:8080";

        // Fetch budgets
        List<?> budgets = restTemplate.getForObject(baseUrl + "/budget/find/" + accountId, List.class);

        // Fetch sources
        List<?> sources = restTemplate.getForObject(baseUrl + "/leadSource/find/" + accountId, List.class);

        // Fetch scopes
        List<?> scopes = restTemplate.getForObject(baseUrl + "/leadScope/find/" + accountId, List.class);

        // Fetch statuses
        List<?> statuses = restTemplate.getForObject(baseUrl + "/leadStatus/find/" + accountId, List.class);

        // Prepare the response map
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);
        response.put("budgets", budgets);
        response.put("sources", sources);
        response.put("scopes", scopes);
        response.put("statuses", statuses);

        // Return the response with HTTP 200 status
        return ResponseEntity.ok(response);
    }



    public String loginAccount(String email, String password) {
       Account account=accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
        if (password.equals(account.getPassword())) {
            return jwtUtil.generateToken(email);

        }
        throw new BadCredentialsException("Invalid email or password");

    }

    public String EncodeUsersPassword(String password)
    {
        return passwordEncoder.encode(password);

    }



}

