package com.example.Security.controller;

import com.example.Security.entity.LeadConfig;
import com.example.Security.repository.LeadConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/source")
public class SourceController {

    @Autowired
    private final LeadConfigRepository leadConfigRepository;

    public SourceController(LeadConfigRepository leadConfigRepository) {
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
    public LeadConfig updateLeadSource(@RequestBody Map<String, String> request) {
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

        @DeleteMapping("/delete/{id}")
    public String deleteLeadStatus(@PathVariable Long id) {
        if (leadConfigRepository.existsById(id)) {
            leadConfigRepository.deleteById(id);
            return "LeadConfig with ID " + id + " has been successfully deleted.";
        } else {
            return "LeadConfig with ID " + id + " not found.";
        }
    }

}
