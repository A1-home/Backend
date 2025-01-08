package com.example.Security.controller;

//import com.example.Security.entity.LeadSourceConfig;
import com.example.Security.entity.LeadSources;
import com.example.Security.entity.LeadStatusConfig;
import com.example.Security.repository.LeadSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leadSource")
@CrossOrigin
public class LeadSourceController {

    @Autowired
    private final LeadSourceRepository leadSourceRepository;

    public LeadSourceController(LeadSourceRepository leadSourceRepository) {
        this.leadSourceRepository = leadSourceRepository;
    }

    // API to add a new Lead Source Configuration
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadSources addLeadSource(@RequestBody LeadSources leadSources) {
        return leadSourceRepository.save(leadSources);
    }


    @GetMapping("/find/{accountId}")
    public List<LeadSources> getLeadStatusByAccountId(@PathVariable Long accountId) {
        return leadSourceRepository.findByAccountId(accountId);
    }
}
