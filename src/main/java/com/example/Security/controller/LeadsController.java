package com.example.Security.controller;


import com.example.Security.entity.Leads;
import com.example.Security.entity.Users;
import com.example.Security.repository.LeadsRepository;
import com.example.Security.repository.UsersRepository;
import com.example.Security.service.LeadService;
import com.example.Security.service.PaginationService;
import jdk.jshell.Snippet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/leads")
public class LeadsController {

    private final LeadsRepository leadsRepository;
    @Autowired
    private final LeadService leadService;
    private final UsersRepository usersRepository;
    private final PaginationService paginationService;


    @Autowired
    public LeadsController(LeadsRepository leadsRepository, LeadService leadService, UsersRepository usersRepository, PaginationService paginationService) {
        this.leadsRepository = leadsRepository;
        this.leadService = leadService;
        this.usersRepository = usersRepository;
        this.paginationService = paginationService;
    }



    @PostMapping("/importFromExcel")
    public ResponseEntity<?> importLeadsFromExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) {
        try {
            if (file.isEmpty()) {
                return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
            }

            Users user = usersRepository.findById(userId).orElse(null);
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }

            List<Map<String, String>> invalidLeadsList = leadService.parseExcelFile(file, user);

            return new ResponseEntity<>(Map.of(
                    "message", "Leads imported successfully",
                    "invalidLeads", invalidLeadsList
            ), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while importing leads", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //To check Only
//@GetMapping("/findLeads")
//public List<Leads> get()
//{
//    return leadsRepository.findAll();
//}




//ye wala badhiya h
//    @PostMapping("/add")
//    public ResponseEntity<Map<String, Object>> addLead(@RequestBody Map<String, Object> leadData) {
//        Map<String, Object> responseMap = new HashMap<>();
//        try {
//            // Extract user details from the incoming request
//            List<Map<String, Object>> usersList = (List<Map<String, Object>>) leadData.get("users");
//
//            // Retrieve users from the database based on the userIds provided in the users list
//            List<Users> users = usersList.stream()
//                    .map(userMap -> {
//                        // Convert userId to Long (since it's provided as String in the request)
//                        Long userId = Long.parseLong(userMap.get("userId").toString());
//                        return usersRepository.findById(userId).orElse(null);
//                    })
//                    .filter(Objects::nonNull)
//                    .collect(Collectors.toList());
//
//            // If no users are found, return an error response
//            if (users.isEmpty()) {
//                responseMap.put("status", "failure");
//                responseMap.put("message", "Users not found");
//                return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
//            }
//
//            // Parse the startDate (String) into Date
//            String startDateStr = (String) leadData.get("startDate");
//            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
//
//            // Create the Lead entity based on the provided data
//            Leads lead = new Leads();
//            lead.setCreatedBy((String) leadData.get("createdBy"));
//            lead.setClientName((String) leadData.get("clientName"));
//            lead.setAltClientName((String) leadData.get("altClientName"));
//            lead.setPhoneNo((String) leadData.get("phoneNo"));
//            lead.setPrimaryEmail((String) leadData.get("primaryEmail"));
//            lead.setProjectName((String) leadData.get("projectName"));
//            lead.setDescription((String) leadData.get("description"));
//            lead.setSource((String) leadData.get("source"));
//            lead.setStatus((String) leadData.get("status"));
//            lead.setScope((String) leadData.get("scope"));
//            lead.setAltEmail((String) leadData.get("altEmail"));
//            lead.setAltPhoneNo((String) leadData.get("altPhoneNo"));
//            lead.setBudget((String) leadData.get("budget"));
//            lead.setTags((String) leadData.get("tags"));
//            lead.setStartDate(startDate);  // Set the parsed startDate
//
//            // Extract the accountId from the first user (assuming the first user has the account)
//            Long accountId = users.get(0).getAccount().getAccountId();  // Get the accountId from the first user
//            lead.setAccountId(accountId);  // Set the accountId in the lead
//
//            // Set the users list in the lead
//            lead.setUsers(new ArrayList<>(users));
//
//            // Set the relationship in both directions (Users -> Leads and Leads -> Users)
//            for (Users user : users) {
//                if (user.getLeads() == null) {
//                    user.setLeads(new ArrayList<>());
//                }
//                user.getLeads().add(lead);
//            }
//
//            // Save the lead and users in the database
//            leadsRepository.save(lead);
//            usersRepository.saveAll(users);
//
//            // Prepare success response
//            responseMap.put("status", "success");
//            responseMap.put("message", "Lead added successfully");
//            responseMap.put("lead", lead);
//
//            return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            responseMap.put("status", "failure");
//            responseMap.put("message", "Error occurred while adding lead");
//            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addLead(@RequestBody Map<String, Object> leadData) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // Extract user details from the incoming request
            List<Map<String, Object>> usersList = (List<Map<String, Object>>) leadData.get("users");

            // Retrieve users from the database based on the userIds provided in the users list
            List<Users> users = usersList.stream()
                    .map(userMap -> {
                        // Convert userId to Long (since it's provided as String in the request)
                        Long userId = Long.parseLong(userMap.get("userId").toString());
                        return usersRepository.findById(userId).orElse(null);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // If no users are found, return an error response
            if (users.isEmpty()) {
                responseMap.put("status", "failure");
                responseMap.put("message", "Users not found");
                return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
            }

            // Parse the startDate (String) into Date
            String startDateStr = (String) leadData.get("startDate");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);

            // Create the Lead entity based on the provided data
            Leads lead = new Leads();
            lead.setCreatedBy((String) leadData.get("createdBy"));
            lead.setClientName((String) leadData.get("clientName"));
            lead.setAltClientName((String) leadData.get("altClientName"));
            lead.setPhoneNo((String) leadData.get("phoneNo"));
            lead.setPrimaryEmail((String) leadData.get("primaryEmail"));
            lead.setProjectName((String) leadData.get("projectName"));
            lead.setDescription((String) leadData.get("description"));
            lead.setSource((String) leadData.get("source"));
            lead.setStatus((String) leadData.get("status"));
            lead.setScope((String) leadData.get("scope"));
            lead.setAltEmail((String) leadData.get("altEmail"));
            lead.setAltPhoneNo((String) leadData.get("altPhoneNo"));
            lead.setBudget((String) leadData.get("budget"));
            lead.setTags((String) leadData.get("tags"));
            lead.setStartDate(startDate); // Set the parsed startDate

            // Extract the accountId from the first user (assuming the first user has the account)
            Long accountId = users.get(0).getAccount().getAccountId(); // Get the accountId from the first user
            lead.setAccountId(accountId); // Set the accountId in the lead

            // Check for duplicates within the same accountId
            // Check if the email or phone number exists for the same accountId
            boolean isDuplicate = leadsRepository.existsByAccountIdAndPrimaryEmailOrAccountIdAndPhoneNo(
                    accountId, lead.getPrimaryEmail(), accountId, lead.getPhoneNo()
            );

            if (isDuplicate) {
                // Mark as duplicate by setting the tag "D"
                lead.setTags("Duplicate");
            }


            // Set the users list in the lead
            lead.setUsers(new ArrayList<>(users));

            // Set the relationship in both directions (Users -> Leads and Leads -> Users)
            for (Users user : users) {
                if (user.getLeads() == null) {
                    user.setLeads(new ArrayList<>());
                }
                user.getLeads().add(lead);
            }

            // Save the lead and users in the database
            leadsRepository.save(lead);
            usersRepository.saveAll(users);

            // Prepare success response
            responseMap.put("status", "success");
            responseMap.put("message", "Lead added successfully");
            responseMap.put("lead", lead);

            return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "failure");
            responseMap.put("message", "Error occurred while adding lead");
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @PostMapping("/addRemarks")
    public String addRemarks(@RequestBody Map<String, Object> requestData) {
        try {
            // Extract userId, leadId, userName, and remarks from the request body
            Long userId = Long.parseLong(requestData.get("userId").toString());
            Long leadId = Long.parseLong(requestData.get("leadId").toString());
            String userName = requestData.get("userName").toString();
            String remarks = requestData.get("remarks").toString();

//            System.out.println(userId);

            // Fetch the lead from the database
            Optional<Leads> leadOptional = leadsRepository.findById(leadId);
            if (!leadOptional.isPresent()) {
                return "Lead not found!";
            }

            Leads lead = leadOptional.get();

            // Fetch current remarks
            String currentRemarks = lead.getRemarks();

            // Format the current date and time in IST
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            String currentDateTime = dateFormat.format(new Date());

            // Construct the new remark in the correct format
            String newRemark = userId.toString() + "|" + userName + "|" + currentDateTime + "|" + remarks;

            // Append the new remark to the existing remarks using a delimiter
            if (currentRemarks != null && !currentRemarks.isEmpty()) {
                currentRemarks += ";" + newRemark;
            } else {
                currentRemarks = newRemark;
            }

            // Update the lead's remarks and save it
            lead.setRemarks(currentRemarks);
//            System.out.println(currentRemarks);
            leadsRepository.save(lead);

            return "Remark added successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while adding the remark.";
        }
    }



//
//
//    @GetMapping("/filter/assigned-to")
//    public ResponseEntity<Map<String, Object>> filterByAssignedTo(
//            @RequestParam(required = false) Long assignedTo,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "8") int size) {
//        Map<String, Object> response = paginationService.getPaginatedLeadsByAssignedTo(assignedTo, page, size);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/filter/source")
//    public ResponseEntity<Map<String, Object>> filterBySource(
//            @RequestParam(required = false) String source,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "8") int size) {
//        Map<String, Object> response = paginationService.getPaginatedLeadsBySource(source, page, size);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/filter/created-date")
//    public ResponseEntity<Map<String, Object>> filterByCreatedDate(
//            @RequestParam String createdDate,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "8") int size) {
//        // Fetch the paginated data from the service
//        Map<String, Object> response = paginationService.getPaginatedLeadsByCreatedDate(createdDate, page, size);
//
//        // Check if there are no leads
//        if (!response.containsKey("leads") || ((List<?>) response.get("leads")).isEmpty()) {
//            // Return an empty response with 200 status
//            response.put("totalItems", 0);
//            response.put("leads", Collections.emptyList());
//            response.put("totalPages", 0);
//            response.put("currentPage", page);
//        }
//
//        return ResponseEntity.ok(response);
//    }


    @PostMapping("/filter")
    public ResponseEntity<Map<String, Object>> filterLeads(@RequestBody Map<String, Object> filters) {
        Long assignedTo = null;
        Long accountId=null;
//        System.out.println(filters);
        if (filters.get("accountId") != null) {
            accountId = ((Number) filters.get("accountId")).longValue();
        }
        // Ensure that assignedTo is parsed as a Long
        if (filters.get("assignedTo") != null) {
            try {
                assignedTo = Long.valueOf(filters.get("assignedTo").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid assignedTo value. Must be a valid number."));
            }
        }

        String source = (String) filters.getOrDefault("source", null);
        String createdDate = (String) filters.getOrDefault("createdDate", null);
        String lastDate = filters.get("lastDate") != null ? (String) filters.get("lastDate") : null;
        int page = (Integer) filters.getOrDefault("page", 0);
        int size = (Integer) filters.getOrDefault("size", 8);

        // Call PaginationService to get the filtered leads with pagination
        Map<String, Object> response = paginationService.getPaginatedLeads(accountId,assignedTo, source, createdDate, lastDate, page, size);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/followup")
    public String followup(@RequestBody Map<String, Object> requestData) {
        try {
            // Extract data from the request map
            String date = (String) requestData.get("date"); // Date part (yyyy-MM-dd)
            String time = (String) requestData.get("time"); // Time part (HH:mm)
            Long leadId = Long.valueOf(requestData.get("leadId").toString());

            // Validate and parse the date and time
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateTimeFormat.setLenient(false); // Ensure strict date parsing

            Date followUpDate;
            try {
                // Combine date and time into a single string and parse
                String followUp = date + " " + time;
                followUpDate = dateTimeFormat.parse(followUp);
            } catch (ParseException e) {
                return "Invalid date-time format! Please use yyyy-MM-dd for the date and HH:mm for the time.";
            }

            // Format the date and time as a string in the desired format
            String formattedFollowUpDate = dateTimeFormat.format(followUpDate);

            // Fetch the lead from the database
            Optional<Leads> leadOptional = leadsRepository.findById(leadId);
            if (!leadOptional.isPresent()) {
                return "Lead not found!";
            }

            Leads lead = leadOptional.get();

            // Overwrite the follow-up date directly
            lead.setFollowupDate(formattedFollowUpDate);

            // Save the updated lead
            leadsRepository.save(lead);

            return "Follow-up updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while updating the follow-up.";
        }
    }




//    aage se account Id bhi lenge leads find karne ke liye

//
//    @GetMapping("/findAll")
//    public ResponseEntity<?> findAllLeads(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "8") int size) {
//        try {
//            // Call the service to get paginated data
//            Map<String, Object> response = paginationService.getPaginatedLeads(page, size);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("An error occurred while fetching leads", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }





    @GetMapping("/findById/{leadId}")
    public Optional<Leads> LeadById(@PathVariable("leadId") Long leadId)
    {
        return leadsRepository.findById(leadId);
    }




    @PutMapping("/updateStatus")
    public String updateLeadStatus(@RequestBody Map<String, String> request) {
        Long id = Long.parseLong(request.get("leadId"));

        Leads leads = leadsRepository.findById(id).orElse(null);

        if (leads != null) {
            if (request.containsKey("status")) {
                leads.setStatus(request.get("status"));
                leadsRepository.save(leads);
            }

            return "Status updated";
        } else {
            throw new RuntimeException("Lead with ID " + id + " not found");
        }
    }


    @PutMapping("/updateBudget")
    public String updateLeadBudget(@RequestBody Map<String, String> request) {
        Long id = Long.parseLong(request.get("leadId"));

        Leads leads = leadsRepository.findById(id).orElse(null);

        if (leads != null) {
            if (request.containsKey("budget")) {
                leads.setBudget(request.get("budget"));
                leadsRepository.save(leads);
            }

            return "Budget Updated";
        } else {
            throw new RuntimeException("Lead with ID " + id + " not found");
        }
    }

    @PutMapping("/updateScope")
    public String updateLeadScope(@RequestBody Map<String, String> request) {
        Long id = Long.parseLong(request.get("leadId"));

        Leads leads = leadsRepository.findById(id).orElse(null);

        if (leads != null) {
            if (request.containsKey("scope")) {
                leads.setScope(request.get("scope"));
                leadsRepository.save(leads);
            }

            return "Scope Updated";
        } else {
            throw new RuntimeException("Lead with ID " + id + " not found");
        }
    }


    @PutMapping("/updateSource")
    public Leads updateLeadSource(@RequestBody Map<String, String> request) {
        Long id = Long.parseLong(request.get("leadId"));

        Leads leads = leadsRepository.findById(id).orElse(null);

        if (leads != null) {
            if (request.containsKey("source")) {
                leads.setSource(request.get("source"));
                leadsRepository.save(leads);
            }

            return leads;
        } else {
            throw new RuntimeException("Lead with ID " + id + " not found");
        }
    }




//@GetMapping("/searchLeads")
//public ResponseEntity<?> searchLeadsByFlexibleName(
//        @RequestParam("name") String name,
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "8") int size) {
//    try {
//        // Call the service method for paginated search
//        Map<String, Object> response = paginationService.getPaginatedLeadsBySearchName(name, page, size);
//
//        // Check if results are found
//        if (response.get("leads") == null || ((List<Leads>) response.get("leads")).isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No leads found with the given criteria.");
//        }
//
//        // Return the paginated response
//        return ResponseEntity.ok(response);
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching leads.");
//    }
//}
//

    @GetMapping("/searchLeads")
    public ResponseEntity<?> searchLeadsByFlexibleName(
            @RequestParam("name") String name,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        try {
            // Call the service method for paginated search
            Map<String, Object> response = paginationService.getPaginatedLeadsBySearchName(name, page, size);

            // Check if results are found
            if (response.get("leads") == null || ((List<Leads>) response.get("leads")).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No leads found with the given criteria.");
            }

            // Return the paginated response
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching leads.");
        }
    }

    @PostMapping("/assignUsers")
    public ResponseEntity<String> assignLeads(@RequestBody List<Integer> usersIds, @RequestParam Integer leadId) {
        // Retrieve the lead by leadId
        Leads lead = leadsRepository.findById(Long.valueOf(leadId))
                .orElseThrow(() -> new RuntimeException("Lead not found with ID: " + leadId));

        // Fetch all users by their IDs
        List<Users> newUsers = (List<Users>) usersRepository.findAllById(usersIds);

        if (newUsers.isEmpty()) {
            return ResponseEntity.badRequest().body("No users found for the provided IDs.");
        }

        // Get the existing users of the lead
        List<Users> existingUsers = lead.getUsers();

        if (existingUsers == null) {
            existingUsers = new ArrayList<>();
        }

        // Add new users to the existing ones without duplicating
        for (Users user : newUsers) {
            if (!existingUsers.contains(user)) {
                existingUsers.add(user);
            }
        }

        // Update the lead with the combined users list
        lead.setUsers(existingUsers);

        // Set the relationship in both directions
        for (Users user : newUsers) {
            if (user.getLeads() == null) {
                user.setLeads(new ArrayList<>());
            }
            if (!user.getLeads().contains(lead)) {
                user.getLeads().add(lead);
            }
        }

        // Save the lead and users in the database
        leadsRepository.save(lead);
        usersRepository.saveAll(newUsers);

        return ResponseEntity.ok("Users successfully assigned to the lead.");
    }



    @PatchMapping("/updateLeads/{leadId}")
    public ResponseEntity<Leads> updateLeadDetails(
            @PathVariable Long leadId,
            @RequestBody Map<String, String> updates) {
        Leads updatedLead = leadService.updateLeadDetails(leadId, updates);
        return ResponseEntity.ok(updatedLead);
    }








}
