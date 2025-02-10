package com.example.Security.controller;

import com.example.Security.DTO.QuotationLineItemDTO;
import com.example.Security.entity.QuotationLineItem;
import com.example.Security.entity.Quotations;
import com.example.Security.repository.QuotationsLineItemRepository;
import com.example.Security.repository.QuotationsRepository;
import com.example.Security.service.JwtUtil;
import com.example.Security.service.QuotationItemListService;
import com.example.Security.service.QuotationLineItemService;
import com.example.Security.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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

    @Autowired
    private  JwtUtil jwtUtil;

    @Autowired
    private QuotationItemListService quotationItemListService;
    @Autowired
    private QuotationLineItemService quotationLineItemService;

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


    @PostMapping("/save")
    public ResponseEntity<?> saveQuotationLineItem(
            @RequestBody QuotationLineItemDTO dto){
        try {
            // Extract accountId from the token
          Boolean isAddtoMainList=  dto.getAddToMainList();
          Long quotationId= (dto.getQuotationId());
          System.out.println("quotationId "+quotationId);
          if(quotationId == 0 )
          {
             dto.setQuotationId( quotationService.addNewQuotation(dto));
             System.out.println(dto.getQuotationId());
          }


            if(isAddtoMainList)
            {
               quotationItemListService.saveQuotationItemList(dto);
            }

            // Save the quotation line item
            QuotationLineItem savedItem = quotationLineItemService.saveQuotationLineItem(dto);

            // Return the saved item
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving quotation line item: " + e.getMessage());
        }
    }

    @GetMapping("/findByQuotId/{quotId}")
    public ResponseEntity<Object> findListItem(@PathVariable("quotId") Long quotId) {
//        System.out.println(quotId);
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
