package com.example.Security.controller;


import com.example.Security.entity.LeadConfig;
import com.example.Security.repository.LeadConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/scope")
@RestController
public class ScopeController {

    @Autowired
    private final LeadConfigRepository leadConfigRepository;

    public ScopeController(LeadConfigRepository leadConfigRepository) {
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

    @PutMapping("/update")
    public LeadConfig updateLeadStatus(@RequestBody Map<String, String> request) {
        Long id = Long.parseLong(request.get("id"));

        LeadConfig leadConfig = leadConfigRepository.findById(id).orElse(null);

        if (leadConfig != null) {
            if (request.containsKey("name")) {
                leadConfig.setName(request.get("name"));
            }

            return leadConfigRepository.save(leadConfig);
        } else {
            throw new RuntimeException("LeadConfig with ID " + id + " not found");
        }
    }



}
