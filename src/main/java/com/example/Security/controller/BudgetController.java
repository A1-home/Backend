package com.example.Security.controller;


import com.example.Security.entity.LeadConfig;
import com.example.Security.repository.LeadConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/budget")
@RestController
public class BudgetController {

    @Autowired
    private final LeadConfigRepository leadConfigRepository;

    public BudgetController(LeadConfigRepository leadConfigRepository) {
        this.leadConfigRepository = leadConfigRepository;
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadConfig addLeadStatus(@RequestBody LeadConfig leadConfig) {
        return leadConfigRepository.save(leadConfig);
    }

    @GetMapping("/find/{accountId}/{type}")
    public List<LeadConfig> getLeadStatusByAccountIdAndType(@PathVariable Long accountId, @PathVariable String type) {
        return leadConfigRepository.findByAccountIdAndType(accountId, type);
    }


}
