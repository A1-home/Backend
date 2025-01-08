package com.example.Security.controller;

import com.example.Security.entity.LeadStatusConfig;
import com.example.Security.repository.LeadStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leadStatus")
@CrossOrigin
public class LeadStatusController {

    @Autowired
    private final LeadStatusRepository leadStatusRepository;

    public LeadStatusController(LeadStatusRepository leadStatusRepository) {
        this.leadStatusRepository = leadStatusRepository;
    }

    // API to add a new Lead Status Configuration
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadStatusConfig addLeadStatus(@RequestBody LeadStatusConfig leadStatusConfig) {
        return leadStatusRepository.save(leadStatusConfig);
    }

    @GetMapping("/find/{accountId}")
    public List<LeadStatusConfig> getLeadStatusByAccountId(@PathVariable Long accountId) {
        return leadStatusRepository.findByAccountId(accountId);
    }

}
