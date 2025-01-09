package com.example.Security.controller;

import com.example.Security.entity.BudgetRange;
import com.example.Security.entity.LeadStatusConfig;
import com.example.Security.repository.BudgetRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/leadBudget")
public class BudgetRangeController {

    @Autowired
    private final BudgetRangeRepository budgetRangeRepository;

    public BudgetRangeController(BudgetRangeRepository budgetRangeRepository) {
        this.budgetRangeRepository = budgetRangeRepository;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addBudgetRange(@RequestBody BudgetRange budgetRange) {
        try {
            // Save the budgetRange to the repository
            BudgetRange savedBudgetRange = budgetRangeRepository.save(budgetRange);

            // Create a success message
            Map<String, String> response = new HashMap<>();
            response.put("message", "Budget range added successfully!");
            response.put("status", "success");

            // Return the response with the saved budgetRange and the success message
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while adding budget range", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/find/{accountId}")
    public List<BudgetRange> getLeadStatusByAccountId(@PathVariable Long accountId) {
        return budgetRangeRepository.findByAccountId(accountId);
    }



}
