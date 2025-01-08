package com.example.Security.controller;

import com.example.Security.entity.Account;
import com.example.Security.entity.Leads;
import com.example.Security.entity.Users;
import com.example.Security.repository.AccountRepository;
import com.example.Security.repository.LeadsRepository;
import com.example.Security.repository.UsersRepository;
import com.example.Security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private AuthService authService;

    @Autowired
    private LeadsRepository leadsRepository;

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






    @GetMapping("/leadsOf/{userId}")
    public ResponseEntity<?> getUserClients(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            // Fetch user by ID
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Fetch all leads associated with the user
            List<Leads> userLeads = user.getLeads();

            if (userLeads.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No clients found for this user.");
            }

            // Use pagination logic on the list of user leads
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, userLeads.size());

            if (startIndex >= userLeads.size()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Page out of range.");
            }

            // Create a sublist for the current page
            List<Leads> paginatedLeads = userLeads.subList(startIndex, endIndex);

            // Build the response
            Map<String, Object> response = new HashMap<>();
            response.put("totalItems", userLeads.size());
            response.put("leads", paginatedLeads);
            response.put("totalPages", (int) Math.ceil((double) userLeads.size() / size));
            response.put("currentPage", page);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching leads.");
        }
    }



    @GetMapping("/getAll")
    public List<Users> getAllUsers()
    {
        return (List<Users>) usersRepository.findAll();
    }




    @GetMapping("/searchLeads")
    public ResponseEntity<?> searchLeadsByFlexibleName(
            @RequestParam("name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            // Search leads by flexible name
            List<Leads> matchingLeads = leadsRepository.searchFlexibleLeadsByName(name);

            if (matchingLeads.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No leads found with the given name.");
            }

            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, matchingLeads.size());

            if (startIndex >= matchingLeads.size()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Page out of range.");
            }

            // Create a sublist for the current page
            List<Leads> paginatedLeads = matchingLeads.subList(startIndex, endIndex);

            // Build the response
            Map<String, Object> response = new HashMap<>();
            response.put("totalItems", matchingLeads.size());
            response.put("leads", paginatedLeads);
            response.put("totalPages", (int) Math.ceil((double) matchingLeads.size() / size));
            response.put("currentPage", page);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching leads.");
        }
    }







}
