package com.example.Security.controller;

import com.example.Security.entity.PaymentPlan;
import com.example.Security.entity.TermsConditonDetails;
import com.example.Security.entity.TermsCondition;
import com.example.Security.repository.TermConditionDetailsRepository;
import com.example.Security.repository.TermConditionRepository;
import com.example.Security.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/termCondition")
public class TermConditionController {



    @Autowired
    private final TermConditionRepository termConditionRepository;

    @Autowired
    private  final TermConditionDetailsRepository termConditionDetailsRepository;


    @Autowired
    private  final JwtUtil jwtUtil;
    @Autowired
    public TermConditionController(TermConditionRepository termConditionRepository, TermConditionDetailsRepository termConditionDetailsRepository, JwtUtil jwtUtil) {
        this.termConditionRepository = termConditionRepository;
        this.termConditionDetailsRepository = termConditionDetailsRepository;
        this.jwtUtil = jwtUtil;
    }

    // Save TermsCondition (Bidirectional save with Details)
    @PostMapping("/save")
    public ResponseEntity<TermsCondition> saveTermCondition(@RequestBody TermsCondition termsCondition) {
        // Ensure that termsCondition is set in each TermsConditonDetails for bidirectional relationship
        if (termsCondition.getTermsConditonDetails() != null) {
            for (TermsConditonDetails details : termsCondition.getTermsConditonDetails()) {
                details.setTermsCondition(termsCondition);  // Set the back reference for bidirectional relationship
            }
        }

        TermsCondition savedTermCondition = termConditionRepository.save(termsCondition);
        return new ResponseEntity<>(savedTermCondition, HttpStatus.CREATED);
    }


    @PutMapping("/{id}/addDetails")
    public ResponseEntity<TermsCondition> addDetailsToTermCondition(
            @PathVariable Long id, @RequestBody List<TermsConditonDetails> newDetails) {

        Optional<TermsCondition> optionalTermsCondition = termConditionRepository.findById(id);

        if (optionalTermsCondition.isPresent()) {
            TermsCondition termsCondition = optionalTermsCondition.get();

            // Set TermsCondition reference for each new TermsConditonDetails entry
            for (TermsConditonDetails details : newDetails) {
                details.setTermsCondition(termsCondition);
            }

            // Add new details to the existing list
            termsCondition.getTermsConditonDetails().addAll(newDetails);

            // Save updated TermsCondition
            TermsCondition updatedTermsCondition = termConditionRepository.save(termsCondition);

            return new ResponseEntity<>(updatedTermsCondition, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all TermsConditions by accountId
    @GetMapping("/getAll")
    public ResponseEntity<List<TermsCondition>> getTermsConditionsByAccountId() {

        Map<String, Object> userDetails = jwtUtil.extractUserDetails();

        Long accountId = (Long) userDetails.get("accountId");


        List<TermsCondition> termsConditions = termConditionRepository.findByAccountId(accountId);
        if (termsConditions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(termsConditions, HttpStatus.OK);
        }
    }

    // Get a specific TermsCondition by id
    @GetMapping("/getById/{id}")
    public ResponseEntity<TermsCondition> getTermConditionById(@PathVariable Long id) {
        Optional<TermsCondition> termCondition = termConditionRepository.findById(id);
        if (termCondition.isPresent()) {
            return new ResponseEntity<>(termCondition.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // Update TermsCondition (Optional)


    @DeleteMapping("/deleteTermCondition/{id}")
    public ResponseEntity<Void> deleteTermConditionWithDetails(@PathVariable Long id) {
        Optional<TermsCondition> termConditionOptional = termConditionRepository.findById(id);
        if (termConditionOptional.isPresent()) {
            TermsCondition termsCondition = termConditionOptional.get();

            // Delete all associated details first
            termConditionDetailsRepository.deleteAll(termsCondition.getTermsConditonDetails());

            // Delete the parent TermsCondition
            termConditionRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/deleteDetail/{detailsId}")
    public ResponseEntity<Void> deleteSingleTermConditionDetail(@PathVariable Long detailsId) {
        if (termConditionDetailsRepository.existsById(detailsId)) {
            termConditionDetailsRepository.deleteById(detailsId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
