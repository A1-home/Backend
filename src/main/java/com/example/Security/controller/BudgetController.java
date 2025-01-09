package com.example.Security.controller;


import com.example.Security.entity.LeadConfig;
import com.example.Security.repository.LeadConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @DeleteMapping("/delete/{Id}")
    public String deleteLeadConfig(@PathVariable("Id") Long Id) {

        Optional<LeadConfig> leadConfig=leadConfigRepository.findById(Id);
//        if(leadConfig!=null && leadConfig.)
        if (leadConfigRepository.existsById(Id)) {
            leadConfigRepository.deleteById(Id);
            return "successfully deleted.";
        } else {
            return "Error while deleting.";
        }
    }


    @PutMapping("/update")
    public LeadConfig updateBudget(@RequestBody Map<String, String> request) {
        Long id = Long.parseLong(request.get("id"));
        String name = request.get("budget");

        LeadConfig leadConfig = leadConfigRepository.findById(id).orElse(null);

        if (leadConfig != null) {
            leadConfig.setName(name);
            return leadConfigRepository.save(leadConfig);
        } else {
            throw new RuntimeException("LeadConfig with ID " + id + " not found");
        }
    }








}
