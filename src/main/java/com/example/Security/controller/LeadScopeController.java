package com.example.Security.controller;

//import com.example.Security.entity.LeadScopeConfig;
import com.example.Security.entity.LeadScopes;
import com.example.Security.entity.LeadStatusConfig;
import com.example.Security.repository.LeadScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leadScope")
@CrossOrigin
public class LeadScopeController {

    @Autowired
    private final LeadScopeRepository leadScopeRepository;

    public LeadScopeController(LeadScopeRepository leadScopeRepository) {
        this.leadScopeRepository = leadScopeRepository;
    }

    // API to add a new Lead Scope Configuration
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadScopes addLeadScope(@RequestBody LeadScopes leadScope) {
        return leadScopeRepository.save(leadScope);
    }


    @GetMapping("/find/{accountId}")
    public List<LeadScopes> getLeadStatusByAccountId(@PathVariable Long accountId) {
        return leadScopeRepository.findByAccountId(accountId);
    }
}
