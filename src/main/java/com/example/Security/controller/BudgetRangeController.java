package com.example.Security.controller;

import com.example.Security.entity.BudgetRange;
import com.example.Security.entity.LeadStatusConfig;
import com.example.Security.repository.BudgetRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/budget")
public class BudgetRangeController {

    @Autowired
    private final BudgetRangeRepository budgetRangeRepository;

    public BudgetRangeController(BudgetRangeRepository budgetRangeRepository) {
        this.budgetRangeRepository = budgetRangeRepository;
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetRange addBudgetRange(@RequestBody BudgetRange budgetRange) {
        return budgetRangeRepository.save(budgetRange);
    }


    @GetMapping("/find/{accountId}")
    public List<BudgetRange> getLeadStatusByAccountId(@PathVariable Long accountId) {
        return budgetRangeRepository.findByAccountId(accountId);
    }

}
