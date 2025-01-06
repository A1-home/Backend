package com.example.Security.service;

import com.example.Security.entity.Leads;
import com.example.Security.repository.LeadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaginationService {

    private final LeadsRepository leadsRepository;

    @Autowired
    public PaginationService(LeadsRepository leadsRepository) {
        this.leadsRepository = leadsRepository;
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
