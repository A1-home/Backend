package com.example.Security.service;

import com.example.Security.entity.Leads;
import com.example.Security.repository.LeadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaginationService {

    private final LeadsRepository leadsRepository;

    @Autowired
    public PaginationService(LeadsRepository leadsRepository) {
        this.leadsRepository = leadsRepository;
    }


    // Method to get paginated leads with filters
    public Map<String, Object> getPaginatedLeads(Long accountId,Long assignedTo, String source, String createdDate, String lastDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // If lastDate is not provided, set it to today's date
        if (lastDate == null) {
            lastDate = LocalDate.now().toString();  // Get today's date in "YYYY-MM-DD" format
        }

        // Call the appropriate repository method based on the provided filters
        Page<Leads> leadsPage;

        // If both assignedTo and source are provided
        if (assignedTo != null && source != null && createdDate == null) {
            leadsPage = leadsRepository.findByAssignedToAndSource(accountId,assignedTo, source, pageable);
        }
        // If only assignedTo is provided
        else if (assignedTo != null && source == null && createdDate == null) {
            leadsPage = leadsRepository.findByAssignedTo(accountId,assignedTo, pageable);
        }
        // If only source is provided
        else if (source != null && assignedTo == null && createdDate == null) {
            leadsPage = leadsRepository.findBySource(accountId,source, pageable);
        }
        // If createdDate and lastDate are provided
        else if (createdDate != null && lastDate != null && assignedTo == null && source == null) {
            leadsPage = leadsRepository.findByCreatedDateAndLastDate(accountId,createdDate, lastDate, pageable);
        }
        // Default case for all filters
        else {
            leadsPage = leadsRepository.findByFilters(accountId,assignedTo, source, createdDate, lastDate, pageable);
        }

        // Prepare the response for pagination
        return preparePaginationResponse(leadsPage);
    }




    public Map<String, Object> getPaginatedLeadsBySearchName(String name, int page, int size) {
        // Create a Pageable object
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated search results
        Page<Leads> leadsPage = leadsRepository.searchFlexibleLeadsByName(name, pageable);

        // Prepare and return the response
        return preparePaginationResponse(leadsPage);
    }


    private Map<String, Object> preparePaginationResponse(Page<Leads> leadsPage) {
        Map<String, Object> response = new HashMap<>();
        response.put("leads", leadsPage.getContent());
        response.put("currentPage", leadsPage.getNumber());
        response.put("totalItems", leadsPage.getTotalElements());
        response.put("totalPages", leadsPage.getTotalPages());
        return response;
    }

    public Map<String, Object> getPaginatedLeads(int page, int size) {
        // Create a Pageable object
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated results
        Page<Leads> leadsPage = leadsRepository.findAll(pageable);

        // Prepare the response map
        Map<String, Object> response = new HashMap<>();
        response.put("leads", leadsPage.getContent()); // List of leads for the current page
        response.put("currentPage", leadsPage.getNumber());
        response.put("totalItems", leadsPage.getTotalElements());
        response.put("totalPages", leadsPage.getTotalPages());

        return response;
    }




}
