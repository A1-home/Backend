package com.example.Security.service;

import com.example.Security.DTO.QuotationLineItemDTO;
import com.example.Security.entity.Quotations;
import com.example.Security.repository.QuotationsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuotationService {

    @Autowired
    private QuotationsRepository quotationsRepository;
    public String getCellValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    public Double parseDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer parseInteger(String value) {
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Double updateBaseAmount(Double newAmount, Long quotationId) {
        Optional<Quotations> quotation = quotationsRepository.findById(quotationId);

        if (quotation.isPresent()) {
            Quotations quot = quotation.get();
            Double baseAmount = quot.getBaseAmount();
            baseAmount += newAmount;
            quot.setBaseAmount(baseAmount);

            // Save the updated quotation
            quotationsRepository.save(quot);

            return baseAmount;
        } else {
            // Handle case where quotation is not found
            // You can throw an exception or return some default value
            throw new EntityNotFoundException("Quotation not found with ID: " + quotationId);
        }
    }

    public Integer updateItemCount(Long quotationId) {
        Optional<Quotations> quotation = quotationsRepository.findById(quotationId);

        if (quotation.isPresent()) {
            Quotations quot = quotation.get();
            Integer itemCount = quot.getItemCount();
            itemCount += 1;
            quot.setItemCount(itemCount);

            // Save the updated quotation
            quotationsRepository.save(quot);

            return itemCount;
        } else {
            // Handle case where quotation is not found
            // You can throw an exception or return some default value
            throw new EntityNotFoundException("Quotation not found with ID: " + quotationId);
        }
    }


    public Long addNewQuotation(QuotationLineItemDTO quotationLineItemDTO)
    {
        Quotations quotations=new Quotations();
        quotations.setQuotationName("Draft Quotation");
        quotations.setLeadId(quotationLineItemDTO.getLeadId());
        quotations.setBaseAmount(quotationLineItemDTO.getRate()*quotationLineItemDTO.getQuantity());
        quotations.setAccountId(quotationLineItemDTO.getAccountId());
        quotations.setAuthorName(quotationLineItemDTO.getAuthor());
        quotations.setIdDraft(true);
        quotations.setItemCount(1);


       Quotations q= quotationsRepository.save(quotations);
        return q.getId();
    }

}
