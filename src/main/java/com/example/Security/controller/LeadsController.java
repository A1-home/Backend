package com.example.Security.controller;

import com.example.Security.DTO.LeadDTO;
import com.example.Security.entity.Leads;
import com.example.Security.entity.Users;
import com.example.Security.repository.LeadsRepository;
import com.example.Security.repository.UsersRepository;
import com.example.Security.service.LeadService;
import com.example.Security.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/leads")
public class LeadsController {

    private final LeadsRepository leadsRepository;
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



    @PostMapping("/add")
    public ResponseEntity<String> addLead(@RequestBody Leads lead) {
        try {
            // Retrieve users from the database
            List<Users> users = lead.getUsers().stream()
                    .map(user -> usersRepository.findById(user.getUserId()).orElse(null))
                    .filter(Objects::nonNull)  // Exclude null users (not found in DB)
                    .collect(Collectors.toList());

            // Set the relationship in both directions
            lead.setUsers(new ArrayList<>(users)); // Avoid modifying the input list directly

            // Use a separate loop to set the lead in each user
            for (Users user : users) {
                if (user.getLeads() == null) {
                    user.setLeads(new ArrayList<>()); // Ensure the list is initialized
                }
                user.getLeads().add(lead);
            }

            // Save the lead and users
            leadsRepository.save(lead);
            usersRepository.saveAll(users);

            // Return success message
            return new ResponseEntity<>("Lead added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            // Return error message
            return new ResponseEntity<>("Error occurred while adding lead", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @PostMapping("/addRemarks")
//    public String addRemarks(@RequestBody Map<String, Object> requestData) {
//        try {
//            // Extract userId, leadId, userName, and remarks from the request body
//            Long userId = Long.parseLong(requestData.get("userId").toString());
//            Long leadId = Long.parseLong(requestData.get("leadId").toString());
//            String userName = requestData.get("userName").toString();
//            String remarks = requestData.get("remarks").toString();
//
//            System.out.println(userId);
//
//            // Fetch the lead from the database
//            Optional<Leads> leadOptional = leadsRepository.findById(leadId);
//            if (!leadOptional.isPresent()) {
//                return "Lead not found!";
//            }
//
//            Leads lead = leadOptional.get();
//
//            // Fetch current remarks
//            String currentRemarks = lead.getRemarks();
//
//            // Format the current date and time in IST
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
//            String currentDateTime = dateFormat.format(new Date());
//
//            // Construct the new remark in the correct format
//            String newRemark = userId.toString() + "|" + userName + "|" + currentDateTime + "|" + remarks;
//
//            // Append the new remark to the existing remarks using a delimiter
//            if (currentRemarks != null && !currentRemarks.isEmpty()) {
//                currentRemarks += ";" + newRemark;
//            } else {
//                currentRemarks = newRemark;
//            }
//
//            // Update the lead's remarks and save it
//            lead.setRemarks(currentRemarks);
//            System.out.println(currentRemarks);
//            leadsRepository.save(lead);
//
//            return "Remark added successfully!";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "An error occurred while adding the remark.";
//        }
//    }

    @PutMapping("/updateRemarks")
    public String updateRemarks(@RequestBody Map<String, Object> requestData) {
        try {
            // Extract userId, leadId, userName, and remarks from the request body
            Long userId = Long.parseLong(requestData.get("userId").toString());
            Long leadId = Long.parseLong(requestData.get("leadId").toString());
            String userName = requestData.get("userName").toString();
            String remarks = requestData.get("remarks").toString();

            System.out.println(userId);

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
            System.out.println(currentRemarks);
            leadsRepository.save(lead);

            return "Remark updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while updating the remark.";
        }
    }





    @PostMapping("/followup/{followUp}/{leadId}/{userId}")
    public String followup(@PathVariable("followUp") String followUp,
                           @PathVariable("leadId") Long leadId,
                           @PathVariable("userId") Long userId) {
        try {
            // Validate and parse the follow-up date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateFormat.setLenient(false); // Ensure strict date parsing

            Date followUpDate;
            try {
                followUpDate = dateFormat.parse(followUp);
            } catch (ParseException e) {
                return "Invalid date format! Please use dd-MM-yyyy.";
            }

            // Format the date as a string in the desired format
            String formattedFollowUpDate = dateFormat.format(followUpDate);

            // Fetch the lead from the database
            Optional<Leads> leadOptional = leadsRepository.findById(leadId);
            if (!leadOptional.isPresent()) {
                return "Lead not found!";
            }

            Leads lead = leadOptional.get();

            // Fetch current follow-up data
            String currentFollowupDate = lead.getFollowupDate();

            // Construct the new follow-up entry in the format: userId|date
            String newFollowupEntry = userId + "|" + formattedFollowUpDate;

            // If there is no current follow-up data, add the new entry directly
            if (currentFollowupDate == null || currentFollowupDate.isEmpty()) {
                currentFollowupDate = newFollowupEntry;
            } else {
                // Parse current entries to check for an existing entry by the same user
                StringBuilder updatedFollowup = new StringBuilder();
                boolean updated = false;

                String[] entries = currentFollowupDate.split(";");
                for (String entry : entries) {
                    // Check if the entry is for the same user
                    if (entry.startsWith(userId + "|")) {
                        // Update the existing user's follow-up entry with the new date
                        updatedFollowup.append(newFollowupEntry).append(";");
                        updated = true;
                    } else {
                        // Retain the existing entry
                        updatedFollowup.append(entry).append(";");
                    }
                }

                // If no existing entry for the user was found, append the new entry
                if (!updated) {
                    updatedFollowup.append(newFollowupEntry).append(";");
                }

                // Remove the trailing semicolon and set the updated string
                currentFollowupDate = updatedFollowup.toString();
                if (currentFollowupDate.endsWith(";")) {
                    currentFollowupDate = currentFollowupDate.substring(0, currentFollowupDate.length() - 1);
                }
            }

            // Update the lead's follow-up date and save it
            lead.setFollowupDate(currentFollowupDate);
            leadsRepository.save(lead);

            return "Follow-up updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while updating the follow-up.";
        }
    }



    @GetMapping("/findAll")
    public ResponseEntity<?> findAllLeads(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        try {
            // Call the service to get paginated data
            Map<String, Object> response = paginationService.getPaginatedLeads(page, size);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while fetching leads", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/LeadWithRemarks/{leadId}")
    public ResponseEntity<?> findLeadwithRemarks(@PathVariable("leadId") Long leadId) {
        try {
            LeadDTO leadDTO = leadService.findLeadById(leadId);
            return ResponseEntity.ok(leadDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/findById/{leadId}")
    public Optional<Leads> LeadById(@PathVariable("leadId") Long leadId)
    {
        return leadsRepository.findById(leadId);
    }






}
