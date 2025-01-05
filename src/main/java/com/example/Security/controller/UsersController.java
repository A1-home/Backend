package com.example.Security.controller;

import com.example.Security.entity.Account;
import com.example.Security.entity.Leads;
import com.example.Security.entity.Users;
import com.example.Security.repository.AccountRepository;
import com.example.Security.repository.UsersRepository;
import com.example.Security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private AuthService authService;

    public UsersController(UsersRepository usersRepository, AccountRepository accountRepository) {
        this.usersRepository = usersRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        // Fetch the Account object based on accountId from the request
        Account account = accountRepository.findById(user.getAccount().getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Set the fetched Account object on the user
        user.setAccount(account);
        String encodedPassword=authService.EncodeUsersPassword(user.getPassword());
        user.setPassword(encodedPassword);
        // Save the user to the database
        Users savedUser = usersRepository.save(user);

        // Return the saved user in the response
        return ResponseEntity.ok(savedUser);
    }


    @GetMapping("/clients/{userId}")
    public ResponseEntity<?> getUserClients(@PathVariable("userId") Long userId) {
        // Fetch user by ID
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch all leads associated with this user (from user_lead relationship)
        List<Leads> leads = user.getLeads();

        if (leads.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No clients found for this user.");
        }

        // Return the list of leads (clients) and their details
        return ResponseEntity.ok(leads);
    }










}
