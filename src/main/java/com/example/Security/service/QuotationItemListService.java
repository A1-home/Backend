package com.example.Security.service;

import com.example.Security.DTO.QuotationItemListResponseDTO;
import com.example.Security.DTO.QuotationLineItemDTO;
import com.example.Security.entity.QuotationItemList;
import com.example.Security.entity.QuotationLineItem;
import com.example.Security.repository.QuotationItemListRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class QuotationItemListService {
    @Autowired
    private final QuotationItemListRepository quotationItemListRepository;

    public QuotationItemListService(QuotationItemListRepository quotationItemListRepository) {
        this.quotationItemListRepository = quotationItemListRepository;
    }

    public QuotationItemListResponseDTO findQuotationByAccountId(Long accountId) {
        List<QuotationItemList> items = quotationItemListRepository.findByAccountId(accountId);

        // Convert items to DTOs
        List<QuotationItemList> itemDTOs = items.stream().map(this::mapToQuotationItemDTO).toList();

        // Extract categories and subcategories
        Map<String, List<String>> catAndSubCat = items.stream()
                .collect(Collectors.groupingBy(QuotationItemList::getCategory,
                        Collectors.mapping(QuotationItemList::getSubcategory, Collectors.toList())));

        // Remove duplicates in subcategories
        catAndSubCat.replaceAll((key, value) -> value.stream().distinct().toList());

        // Extract Unit of Measurement
        List<String> unitOfMeasurementList = items.stream()
                .map(QuotationItemList::getUnitOfMeasurement)
                .distinct()
                .toList();

        // Create the response
        QuotationItemListResponseDTO responseDTO = new QuotationItemListResponseDTO();
        responseDTO.setQuotationItemList(itemDTOs);
        responseDTO.setCatAndSubCat(catAndSubCat);
        responseDTO.setUnitOfMeasurementList(unitOfMeasurementList);

        return responseDTO;
    }

    private QuotationItemList mapToQuotationItemDTO(QuotationItemList item) {
        QuotationItemList dto = new QuotationItemList();
        dto.setId(item.getId());
        dto.setAccountId(item.getAccountId());
        dto.setArea(item.getArea());
        dto.setItem(item.getItem());
        dto.setCategory(item.getCategory());
        dto.setSubcategory(item.getSubcategory());
        dto.setSpecification(item.getSpecification());
        dto.setUnitOfMeasurement(item.getUnitOfMeasurement());
        dto.setRate(item.getRate());
        dto.setItemDescription(item.getItemDescription());
        dto.setImageKey(item.getImageKey()); // Directly map imageKey
        return dto;
    }

    public void processExcel(MultipartFile file, Long accountId) throws Exception {
        List<QuotationItemList> quotationItems = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            boolean isHeaderRow = true;
            while (((Iterator<?>) rowIterator).hasNext()) {
                Row row = rowIterator.next();

                if (isHeaderRow) {
                    isHeaderRow = false;
                    continue; // Skip the header row
                }

                QuotationItemList item = new QuotationItemList();
                item.setAccountId(accountId);

                item.setArea(getCellValue(row, 0));
                item.setCategory(getCellValue(row, 1));
                item.setSubcategory(getCellValue(row, 2));
                item.setItem(getCellValue(row, 3));
                item.setUnitOfMeasurement(getCellValue(row, 4));
                item.setRate(getCellValueAsDouble(row, 5));
                item.setItemDescription(getCellValue(row, 6));
                item.setSpecification(getCellValue(row, 7));
                item.setImageKey(getCellValue(row, 8));
                item.setCreatedDate(new Date()); // Current timestamp

                quotationItems.add(item);
            }
        }

        // Save all items to the database
        quotationItemListRepository.saveAll(quotationItems);
    }

    public String saveQuotationItemList(QuotationLineItemDTO dto) {
        try {
            // Create a new QuotationItemList entity
            QuotationItemList lineItem = new QuotationItemList();
            lineItem.setAccountId(dto.getAccountId());
            lineItem.setArea(dto.getArea());
            lineItem.setCategory(dto.getCategory());
            lineItem.setSubcategory(dto.getSubcategory());
            lineItem.setItem(dto.getItem());
            lineItem.setUnitOfMeasurement(dto.getUnitOfMeasurement());
            lineItem.setRate(dto.getRate());
            lineItem.setItemDescription(dto.getDescription());
            lineItem.setSpecification(dto.getSpecification());
            lineItem.setImageKey(dto.getImageKey());

            // Save the entity
            quotationItemListRepository.save(lineItem);

            // Return a success message
            return "Item saved successfully!";
        } catch (Exception e) {
            // Log the error and return an error message
            e.printStackTrace();
            return "Failed to save item: " + e.getMessage();
        }
    }
    private String getCellValue(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        return (cell == null || cell.getCellType() == CellType.BLANK) ? "" : cell.toString().trim();
    }

    private Double getCellValueAsDouble(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }
}
