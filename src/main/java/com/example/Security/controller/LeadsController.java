package com.example.Security.controller;

import com.example.Security.entity.Leads;
import com.example.Security.entity.Users;
import com.example.Security.repository.LeadsRepository;
import com.example.Security.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leads")
public class LeadsController {

    private final LeadsRepository leadsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public LeadsController(LeadsRepository leadsRepository, UsersRepository usersRepository) {
        this.leadsRepository = leadsRepository;
        this.usersRepository = usersRepository;
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



    @PostMapping("/addRemarks")
    public String addRemarks(@RequestBody Map<String, Object> requestData) {
        try {
            // Extract userId, leadId, and remarks from the request body
            Long userId = Long.parseLong(requestData.get("userId").toString());
            Long leadId = Long.parseLong(requestData.get("leadId").toString());
            String remarks = requestData.get("remarks").toString();

            // Fetch the lead from the database
            Optional<Leads> leadOptional = leadsRepository.findById(leadId);
            if (!leadOptional.isPresent()) {
                return "Lead not found!";
            }

            Leads lead = leadOptional.get();

            // Fetch current remarks
            String currentRemarks = lead.getRemarks();

            // Format the current date in dd-MM-yyyy format (hyphen as separator)
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String currentDate = dateFormat.format(new Date());

            // Construct the new remark in the format: userId|date|remark
            String newRemark = userId + "|" + currentDate + "|" + remarks;

            // Append the new remark to the existing remarks using a delimiter (e.g., ";")
            if (currentRemarks != null && !currentRemarks.isEmpty()) {
                currentRemarks += ";" + newRemark;
            } else {
                currentRemarks = newRemark;
            }

            // Update the lead's remarks and save it
            lead.setRemarks(currentRemarks);
            leadsRepository.save(lead);

            return "Remark added successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while adding the remark.";
        }
    }



    @PostMapping("/add/{followUp}/{leadId}/{userId}")
    public String followup(@PathVariable String followUp,
                           @PathVariable Long leadId,
                           @PathVariable Long userId) {
        try {
            // Fetch the lead from the database
            Optional<Leads> leadOptional = leadsRepository.findById(leadId);
            if (!leadOptional.isPresent()) {
                return "Lead not found!";
            }

            Leads lead = leadOptional.get();

            // Fetch current follow-up data
            String currentFollowupDate = lead.getFollowupDate();

            // Construct the new follow-up entry in the format: userId|date
            String newFollowupEntry = userId + "|" + followUp;

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





}
