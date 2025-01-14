package com.example.Security.controller;

import com.example.Security.entity.Account;
import com.example.Security.entity.Leads;
import com.example.Security.entity.Users;
import com.example.Security.repository.AccountRepository;
import com.example.Security.repository.LeadsRepository;
import com.example.Security.repository.UsersRepository;
import com.example.Security.service.AuthService;
import com.example.Security.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    private PaginationService paginationService;

    @Autowired
    private LeadsRepository leadsRepository;

    public UsersController(UsersRepository usersRepository, AccountRepository accountRepository) {
        this.usersRepository = usersRepository;
        this.accountRepository = accountRepository;
    }

//    @PostMapping("/add")
//    public ResponseEntity<Users> addUser(@RequestBody Users user) {
//        // Fetch the Account object based on accountId from the request
//        Account account = accountRepository.findById(user.getAccount().getAccountId())
//                .orElseThrow(() -> new RuntimeException("Account not found"));
//
//        // Set the fetched Account object on the user
//        user.setAccount(account);
//        String encodedPassword=authService.EncodeUsersPassword(user.getPassword());
//        user.setPassword(encodedPassword);
//        // Save the user to the database
//        Users savedUser = usersRepository.save(user);
//
//        // Return the saved user in the response
//        return ResponseEntity.ok(savedUser);
//    }

    @PostMapping("/add")
    public ResponseEntity<Users> addUser(@RequestBody Map<String, Object> userData) {
        // Extract data from the request body map
        String userName = (String) userData.get("userName");
        String email = (String) userData.get("email");
        String phoneNumber = (String) userData.get("phoneNumber");
        String role = (String) userData.get("role");
//        String password = (String) userData.get("password");

        String password="123456";
        // Extract account ID and fetch the associated Account entity
        Map<String, Object> accountData = (Map<String, Object>) userData.get("account");
        Long accountId = Long.valueOf((Integer) accountData.get("accountId"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Create a new Users object and set its properties
        Users user = new Users();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRole(role);
        user.setAccount(account);

        // Encode the password and set it
        String encodedPassword = authService.EncodeUsersPassword(password);
        user.setPassword(encodedPassword);

        // Save the user to the database
        Users savedUser = usersRepository.save(user);

        // Return the saved user as the response
        return ResponseEntity.ok(savedUser);
    }





    @GetMapping("/leadsOf/{userId}")
    public ResponseEntity<?> getUserClients(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
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




    // account id bhi chihiye hoga aage

    @GetMapping("/getAll")
    public List<Users> getAllUsers()
    {
        return (List<Users>) usersRepository.findAll();
    }
//



    @GetMapping("/getAllUsers/{accountId}")
    public List<Users> findAllUsers(@PathVariable("accountId") Long accountId) {
        List<Users> users = usersRepository.findByAccountId(accountId);
  return  users;

    }





//    @GetMapping("/searchLeads")
//    public ResponseEntity<?> searchLeadsByFlexibleName(
//            @RequestParam("name") String name,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size) {
//        try {
//            // Call PaginationService for paginated leads search
//            Map<String, Object> paginatedLeads = paginationService.getPaginatedResults(page, size, name);
//
//            if (paginatedLeads.get("leads")==null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No leads found with the given name.");
//            }
//
//            return ResponseEntity.ok(paginatedLeads);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching leads.");
//        }
//    }


    @GetMapping("/activeUsers/{accountId}")
    public Long countActiveUSer(@PathVariable("accountId") Long accountId)
    {
        return usersRepository.countActiveUsers(accountId);
    }

    @GetMapping("/activeUsersList/{accountId}")
    public  List<Users> activeUsersList(@PathVariable("accountId") Long accountId)
    {
       return usersRepository.findActiveUsersList(accountId);
    }

    @GetMapping("/deactiveUsersList/{accountId}")
    public  List<Users> deactiveUsersList(@PathVariable("accountId") Long accountId)
    {
        return usersRepository.findDeActiveUsersList(accountId);
    }



    @PutMapping("/changeActiveStatus/{userId}/{accountId}")
    public ResponseEntity<Map<String, String>> changeStatusOfUser(@PathVariable Long userId, @PathVariable Long accountId) {
        Map<String, String> response = new HashMap<>();
        try {
            // Retrieve the user by userId and accountId
            Users user = usersRepository.findByUserIdAndAccount_AccountId(userId, accountId)
                    .orElseThrow(() -> new NoSuchElementException("User not found with userId: " + userId + " and accountId: " + accountId));

            // Toggle the isActive status
            user.setActive(!user.getActive());

            // Save the updated user
            usersRepository.save(user);

            // Prepare the response
            response.put("status", "success");
            response.put("message", "User active status updated successfully");
            response.put("isActive", user.getActive().toString());
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            response.put("status", "failure");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("status", "failure");
            response.put("message", "An error occurred while updating user status");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }





}
