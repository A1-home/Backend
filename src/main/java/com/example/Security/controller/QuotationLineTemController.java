package com.example.Security.controller;

import com.example.Security.entity.QuotationLineItem;
import com.example.Security.entity.Quotations;
import com.example.Security.repository.QuotationsLineItemRepository;
import com.example.Security.repository.QuotationsRepository;
import com.example.Security.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/quotationLineItem")
public class QuotationLineTemController {

    @Autowired
    private QuotationsLineItemRepository quotationsLineItemRepository;

    @Autowired
    private  QuatationItemListController quatationItemListController;

    @Autowired
    private QuotationsRepository quotationsRepository;

    @Autowired
    private QuotationService quotationService;


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public QuotationLineItem addLeadStatus(@RequestBody QuotationLineItem quotationLineItem) {
        Long quotationId = quotationLineItem.getQuotationId();
        Double rate = quotationLineItem.getRate();

        if (rate == null || rate == 0) {
            throw new IllegalArgumentException("Please add a valid rate.");
        }

        quotationService.updateBaseAmount(rate, quotationId);
        quotationService.updateItemCount(quotationId);

        return quotationsLineItemRepository.save(quotationLineItem);
    }

    @GetMapping("/findByQuotId/{quotId}")
    public ResponseEntity<Object> findListItem(@PathVariable("quotId") Long quotId) {
        // Fetch list of items for the given quotation ID
        List<QuotationLineItem> lineItems = quotationsLineItemRepository.findByQuotationId(quotId);

        if (lineItems.isEmpty()) {
            // Return meaningful response if no items are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No line items found ");
        }

        // Return the list of line items
        return ResponseEntity.ok(lineItems);
    }


}
