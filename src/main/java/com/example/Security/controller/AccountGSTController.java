package com.example.Security.controller;

import com.example.Security.entity.AccountGST;
import com.example.Security.entity.AdditionalPage;
import com.example.Security.repository.AccountGSTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/accountGST")
public class AccountGSTController {


    @Autowired
    private  AccountGSTRepository accountGSTRepository;


    @PostMapping("/add")
    public ResponseEntity<AccountGST> addGST(@RequestBody AccountGST accountGST) {
        // Check if the account already exists based on the accountId
        AccountGST existingAccountGST = accountGSTRepository.findByAccountId(accountGST.getAccountId());

        if (existingAccountGST != null) {
            // If account exists, update the existing tax value
            existingAccountGST.setTax(accountGST.getTax());
            accountGSTRepository.save(existingAccountGST); // Save the updated account GST
            return ResponseEntity.ok(existingAccountGST); // Return the updated account GST
        } else {
            // If account does not exist, save as a new record
            AccountGST newGst = accountGSTRepository.save(accountGST);
            return ResponseEntity.ok(newGst); // Return the newly created account GST
        }
    }


    @GetMapping("/get")
    public AccountGST getAdditonalPage(@RequestParam Long accountId)
    {
        return  accountGSTRepository.findByAccountId(accountId);
    }





}
