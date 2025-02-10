package com.example.Security.controller;

import com.example.Security.entity.QuotationItemList;
import com.example.Security.repository.QuotationItemListRepository;
import com.example.Security.service.JwtUtil;
import com.example.Security.service.QuotationItemListService;
import com.example.Security.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin
@RestController
@RequestMapping("/quotationItem")
public class QuatationItemListController {

    @Autowired
    private QuotationItemListRepository quotationItemListRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private  JwtUtil jwtUtil;

    @Autowired
    private QuotationItemListService quotationItemListService;

    public QuatationItemListController(QuotationItemListRepository quotationItemListRepository) {
        this.quotationItemListRepository = quotationItemListRepository;
    }
//    @GetMapping("/findByAccountId/{accountId}")
//    public ResponseEntity<QuotationItemListResponseDTO> findQuotation(@PathVariable("accountId") Long accountId) {
//        QuotationItemListResponseDTO response = quotationItemListService.findQuotationByAccountId(accountId);
//        return ResponseEntity.ok(response);
//    }



    @GetMapping("/findByAccountId")
    public ResponseEntity<?> findQuotation() {


        Map<String, Object> userDetails = jwtUtil.extractUserDetails();
        if (userDetails == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long accountId = (Long) userDetails.get("accountId");
        // Fetch the list of items for the given accountId
        List<QuotationItemList> items = quotationItemListRepository.findByAccountId(accountId);

        // Group and structure the data with an array of items under each subcategory
        Map<String, Map<String, Map<String, List<Object>>>> groupedData = items.stream()
                .collect(Collectors.groupingBy(
                        QuotationItemList::getArea, // Group by area
                        Collectors.groupingBy(
                                QuotationItemList::getCategory, // Group by category
                                Collectors.groupingBy(
                                        QuotationItemList::getSubcategory, // Group by subcategory
                                        Collectors.mapping(item -> {
                                            // Create the list for each subcategory
                                            List<Object> itemList = new ArrayList<>();

                                            // Add item details as a map
                                            Map<String, Object> itemDetails = new HashMap<>();
                                            itemDetails.put("items", item.getItem());
                                            itemDetails.put("unitOfMeasurement", Optional.ofNullable(item.getUnitOfMeasurement()).orElse("N/A"));
                                            itemDetails.put("rate", Optional.ofNullable(item.getRate()).orElse(0.0));
                                            itemDetails.put("imageKey", Optional.ofNullable(item.getImageKey()).orElse("N/A"));
                                            itemDetails.put("description", Optional.ofNullable(item.getItemDescription()).orElse("N/A"));
                                            itemDetails.put("specification", Optional.ofNullable(item.getSpecification()).orElse("N/A"));

                                            // Add details to the list
                                            itemList.add(itemDetails);

                                            return itemList; // Return the full list for the item
                                        }, Collectors.toList()) // Collecting items into an array (list)
                                )
                        )
                ));

        // Fetch distinct units of measurement for the given accountId
        List<String> units = quotationItemListRepository.findDistinctUnitOfMeasurementByAccountId(accountId);

        // Create a response object to include both groupedData and units
        Map<String, Object> response = new HashMap<>();
        response.put("groupedData", groupedData); // Add grouped data
        response.put("units", units); // Add units list

        // Return the response object
        return ResponseEntity.ok(response);
    }




    @PostMapping("/upload")
    public ResponseEntity<String> uploadQuotationItemList(
            @RequestParam("file") MultipartFile file,
            @RequestParam("accountId") Long accountId) {
        try {
            quotationItemListService.processExcel(file, accountId);
            return ResponseEntity.ok("Excel file processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to process Excel file: " + e.getMessage());
        }
    }


    // Add a new Quotation Item
//    @PostMapping("/add")
//    public ResponseEntity<Object> addQuotationItem(@RequestBody QuotationItemList quotationItemList) {
//        if (quotationItemList == null) {
//            return new ResponseEntity<>("Request body cannot be null", HttpStatus.BAD_REQUEST);
//        }
//        try {
//            QuotationItemList savedQuotationItem = quotationItemListRepository.save(quotationItemList);
//            return new ResponseEntity<>(savedQuotationItem, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error while saving the quotation item", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/add")
//    public ResponseEntity<Object> addQuotationItem(
//            @RequestPart("quotationItemList") QuotationItemList quotationItemList,
//            @RequestPart("file") MultipartFile file) {
//        if (quotationItemList == null) {
//            return new ResponseEntity<>("Request body cannot be null", HttpStatus.BAD_REQUEST);
//        }
//        try {
//            // Process the file
//            if (file != null && !file.isEmpty()) {
//                String imageKey = s3Service.uploadFile(file);
//                quotationItemList.setImageKey(imageKey);
//                // Logic for handling file (e.g., uploading to S3)
//            }
//
//
//            // Save the quotation item
//            QuotationItemList savedQuotationItem = quotationItemListRepository.save(quotationItemList);
//            return new ResponseEntity<>(savedQuotationItem, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error while saving the quotation item", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @PostMapping("/add")
    public ResponseEntity<Object> addQuotationItem(
            @RequestParam(value = "accountId", required = false) Long accountId,
            @RequestParam(value = "area", required = false) String area,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "subcategory", required = false) String subcategory,
            @RequestParam(value = "item", required = false) String item,
            @RequestParam(value = "unitOfMeasurement", required = false) String unitOfMeasurement,
            @RequestParam(value = "rate", required = false) Double rate,
            @RequestParam(value = "itemDescription", required = false) String itemDescription,
            @RequestParam(value = "specification", required = false) String specification) throws IOException {

        // Validate accountId, if not provided return a bad request
        if (accountId == null) {
            return new ResponseEntity<>("Account ID is required", HttpStatus.BAD_REQUEST);
        }

        // Create a new QuotationItem object and set the fields
        QuotationItemList quotationItem = new QuotationItemList();
        quotationItem.setAccountId(accountId);  // Account ID is mandatory

        // Set the other fields if provided, or use default empty string if missing
        quotationItem.setArea(area != null ? area : "");
        quotationItem.setCategory(category != null ? category : "");
        quotationItem.setSubcategory(subcategory != null ? subcategory : "");
        quotationItem.setItem(item != null ? item : "");
        quotationItem.setUnitOfMeasurement(unitOfMeasurement != null ? unitOfMeasurement : "");
        quotationItem.setRate(rate != null ? rate : 0.0);
        quotationItem.setItemDescription(itemDescription != null ? itemDescription : "");
        quotationItem.setSpecification(specification != null ? specification : "");

        // Handle file upload if a file is present
//        if (file != null && !file.isEmpty()) {
//            String imageKey = s3Service.uploadFile(file);  // Implement this method to upload the file to S3
//            quotationItem.setImageKey(imageKey);
//        } else {
//            // If no file is uploaded, set imageKey as null
//            quotationItem.setImageKey(null);
//        }

        try {
            // Save the QuotationItem object to the database
            QuotationItemList savedQuotationItem = quotationItemListRepository.save(quotationItem);
            return new ResponseEntity<>(savedQuotationItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while saving the quotation item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to handle file upload to S3 (example)

    // Get Quotation Items (either by Account Id or Quotation Item Id)

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateQuotationItem(@PathVariable Long id, @RequestBody QuotationItemList quotationItemList) {
        if (id == null || quotationItemList == null) {
            return new ResponseEntity<>("ID and request body cannot be null", HttpStatus.BAD_REQUEST);
        }
        try {
            Optional<QuotationItemList> existingItem = quotationItemListRepository.findById(id);
            if (!existingItem.isPresent()) {
                return new ResponseEntity<>("Quotation item not found", HttpStatus.NOT_FOUND);
            }
            quotationItemList.setId(id);
            QuotationItemList updatedQuotationItem = quotationItemListRepository.save(quotationItemList);
            return new ResponseEntity<>(updatedQuotationItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while updating the quotation item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a Quotation Item
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteQuotationItem(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>("ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        try {
            Optional<QuotationItemList> existingItem = quotationItemListRepository.findById(id);
            if (!existingItem.isPresent()) {
                return new ResponseEntity<>("Quotation item not found", HttpStatus.NOT_FOUND);
            }
            quotationItemListRepository.deleteById(id);
            return new ResponseEntity<>("Quotation item deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting the quotation item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
