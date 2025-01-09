package com.example.Security.controller;


import com.example.Security.entity.LeadConfig;
import com.example.Security.repository.LeadConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/status")
@RestController
public class StatusController {

     @Autowired
     private final LeadConfigRepository leadConfigRepository;

    public StatusController(LeadConfigRepository leadConfigRepository) {
        this.leadConfigRepository = leadConfigRepository;
    }

    @GetMapping("/find/{accountId}/{type}")
    public List<LeadConfig> getLeadStatusByAccountIdAndType(@PathVariable Long accountId, @PathVariable String type) {
        return leadConfigRepository.findByAccountIdAndType(accountId, type);
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadConfig addLeadStatus(@RequestBody LeadConfig leadConfig) {
        return leadConfigRepository.save(leadConfig);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteLeadStatus(@PathVariable Long id) {
        if (leadConfigRepository.existsById(id)) {
            leadConfigRepository.deleteById(id);
            return "LeadConfig with ID " + id + " has been successfully deleted.";
        } else {
            return "LeadConfig with ID " + id + " not found.";
        }
    }


    @PutMapping("/update")
    public LeadConfig updateLeadStatus(@RequestBody Map<String, String> request) {
        Long id = Long.parseLong(request.get("id"));

        LeadConfig leadConfig = leadConfigRepository.findById(id).orElse(null);

        if (leadConfig != null) {
            if (request.containsKey("name")) {
                leadConfig.setName(request.get("name"));
            }
            if (request.containsKey("colorCode")) {
                leadConfig.setColorCode(request.get("colorCode"));
            }
            return leadConfigRepository.save(leadConfig);
        } else {
            throw new RuntimeException("LeadConfig with ID " + id + " not found");
        }
    }




}
