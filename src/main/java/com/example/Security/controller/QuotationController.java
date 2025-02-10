package com.example.Security.controller;

import com.example.Security.entity.LeadConfig;
import com.example.Security.entity.Quotations;
import com.example.Security.repository.QuotationsRepository;
import com.example.Security.service.QuotationService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/quotations")
public class QuotationController {

    @Autowired
    private QuotationsRepository quotationsRepository;

    @Autowired
    private QuotationService quotationService;
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Quotations addLeadStatus(@RequestBody Quotations quotations) {
        return quotationsRepository.save(quotations);
    }

    @GetMapping("/findByLeadId/{leadId}")
    public List<Quotations> findQuotations(@PathVariable("leadId") Long leadId)
    {
        return  quotationsRepository.findByLeadId(leadId);
    }

    @PutMapping("/updateQuotationName/{id}")
    public ResponseEntity<Object> updateQuotationName(
            @PathVariable("id") Long id,
            @RequestParam("name") String name) {

        Optional<Quotations> optionalQuotation = quotationsRepository.findById(id);


        if (optionalQuotation.isPresent()) {
            Quotations quotation = optionalQuotation.get();
            quotation.setQuotationName(name); // Update the name
            quotationsRepository.save(quotation); // Save the updated quotation


            return ResponseEntity.ok("Quotation name updated successfully for ID: " + id);
        } else {
            // Return meaningful response if no quotation is found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Quotation not found with ID: " + id);
        }







    }








    @GetMapping("/getQuotationIdByLead/{leadId}")
    public Long findQuotationId(@PathVariable("leadId") Long leadId) {
        return quotationsRepository.findQuotationIdByLead(leadId).orElse(0L); // Return 0 if no quotation is found
    }










}
