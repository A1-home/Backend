package com.example.Security.service;

import com.example.Security.DTO.QuotationLineItemDTO;

import com.example.Security.entity.QuotationLineItem;

import com.example.Security.repository.QuotationsLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuotationLineItemService {

    @Autowired
    private QuotationsLineItemRepository quotationLineItemRepository;
    @Autowired
    private QuotationService quotationService;

    public QuotationLineItem saveQuotationLineItem(QuotationLineItemDTO dto) {
        // Map DTO to Entity

//        System.out.println(dto.getDescription());
       Double rate= dto.getRate();
       Integer quantity=dto.getQuantity();
       Double amount=rate*quantity;
       Long quotationId=dto.getQuotationId();
       quotationService.updateBaseAmount(amount, quotationId);
       quotationService.updateItemCount(quotationId);

        QuotationLineItem lineItem = new QuotationLineItem();
        lineItem.setAccountId(dto.getAccountId());
        lineItem.setQuotationId(dto.getQuotationId());
        lineItem.setArea(dto.getArea());
        lineItem.setCategory(dto.getCategory());
        lineItem.setSubcategory(dto.getSubcategory());
        lineItem.setItem(dto.getItem());
        lineItem.setUnitOfMeasurement(dto.getUnitOfMeasurement());
        lineItem.setRate(dto.getRate());
        lineItem.setQuantity(dto.getQuantity());
        lineItem.setItemDescription(dto.getDescription());
        lineItem.setSpecification(dto.getSpecification());
        lineItem.setImageKey(dto.getImageKey());

        // Save the entity
        return quotationLineItemRepository.save(lineItem);
    }
}