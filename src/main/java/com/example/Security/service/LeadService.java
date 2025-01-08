package com.example.Security.service;

import com.example.Security.DTO.FollowupDTO;
import com.example.Security.DTO.LeadDTO;
import com.example.Security.DTO.RemarkDTO;
import com.example.Security.entity.Leads;
import com.example.Security.repository.LeadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadService {

    @Autowired
    private LeadsRepository leadsRepository;

    public LeadDTO findLeadById(Long leadId) {
        Leads lead = leadsRepository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead not found with ID: " + leadId));

        // Map entity to DTO
        return mapToDTO(lead);
    }

    private LeadDTO mapToDTO(Leads lead) {
        // Parse remarks
        List<RemarkDTO> parsedRemarks = lead.getRemarks() != null && !lead.getRemarks().isEmpty()
                ? Arrays.stream(lead.getRemarks().split(";"))
                .map(r -> {
                    String[] parts = r.split("\\|");
                    if (parts.length >= 4) { // Ensure at least 4 parts
                        // Combine any extra parts into the remark field
                        String remark = Arrays.stream(parts, 3, parts.length) // Start from index 3
                                .collect(Collectors.joining("|"));
                        return new RemarkDTO(
                                Long.parseLong(parts[0]), // userId
                                parts[1],                // userName
                                parts[2],                // date
                                remark                   // combined remark
                        );
                    } else {
                        throw new IllegalArgumentException("Invalid remark format: " + r);
                    }
                }).collect(Collectors.toList())
                : null;

        // Parse follow-up dates
        List<FollowupDTO> parsedFollowups = lead.getFollowupDate() != null && !lead.getFollowupDate().isEmpty()
                ? Arrays.stream(lead.getFollowupDate().split(";"))
                .map(f -> {
                    String[] parts = f.split("\\|");
                    if (parts.length >= 2) { // Ensure at least 2 parts
                        return new FollowupDTO(
                                Long.parseLong(parts[0]), // userId
                                parts[1]                 // follow-up date
                        );
                    } else {
                        throw new IllegalArgumentException("Invalid follow-up format: " + f);
                    }
                }).collect(Collectors.toList())
                : null;

        // Return the DTO
        return new LeadDTO(
                lead.getLeadId(),
                lead.getCreatedBy(),
                lead.getClientName(),
                lead.getAltClientName(),
                lead.getPhoneNo(),
                lead.getPrimaryEmail(),
                lead.getProjectName(),
                lead.getDescription(),
                lead.getSource(),
                lead.getStatus(),
                lead.getScope(),
                lead.getAltEmail(),
                lead.getAltPhoneNo(),
                lead.getBudget(),
                lead.getTags(),
                lead.getStartDate(),
                lead.getCreatedAt(),
                parsedRemarks,
                parsedFollowups
        );
    }

}
