package com.example.Security.service;


import com.example.Security.entity.Leads;
import com.example.Security.entity.Users;
import com.example.Security.repository.LeadsRepository;
import com.example.Security.repository.UsersRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeadService {

    @Autowired
    private LeadsRepository leadsRepository;

    @Autowired
    private  UsersRepository usersRepository;








    public List<Map<String, String>> parseExcelFile(MultipartFile file, Users user) throws Exception {
        List<Leads> validLeadsList = new ArrayList<>();
        List<Map<String, String>> invalidLeadsList = new ArrayList<>();

        // Fetch existing leads for the user's account
        Long accountId = user.getAccount().getAccountId();
        List<Leads> existingLeads = leadsRepository.findByAccountId(accountId);

        // Initialize sets for phone numbers and emails
        Set<String> accountPhoneSet = new HashSet<>();
        Set<String> accountEmailSet = new HashSet<>();

        // Populate the sets with data from the database
        for (Leads existingLead : existingLeads) {
            if (existingLead.getPhoneNo() != null) {
                accountPhoneSet.add(existingLead.getPhoneNo());
            }
            if (existingLead.getPrimaryEmail() != null) {
                accountEmailSet.add(existingLead.getPrimaryEmail());
            }
        }

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // Read the header row and map column indices
            Row headerRow = sheet.getRow(0);
            Map<String, Integer> columnIndexMap = new HashMap<>();
            for (Cell cell : headerRow) {
                columnIndexMap.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
            }

            // Iterate through rows to populate leads
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Leads lead = new Leads();
                Map<String, String> invalidLead = new HashMap<>();

                // Populate lead fields
                String clientName = getCellValue(row, columnIndexMap.get("clientname"));
                String phoneNo = getCellValue(row, columnIndexMap.get("phoneno"));
                String primaryEmail = getCellValue(row, columnIndexMap.get("primaryemail"));

                // Add all data to invalidLead map
                invalidLead.put("row", String.valueOf(i));
                invalidLead.put("clientName", clientName == null ? "" : clientName);
                invalidLead.put("phoneNo", phoneNo == null ? "" : phoneNo);
                invalidLead.put("primaryEmail", primaryEmail == null ? "" : primaryEmail);

                // Validation for mandatory fields
                if (clientName == null || clientName.isEmpty() ||
                        phoneNo == null || phoneNo.isEmpty() ||
                        primaryEmail == null || primaryEmail.isEmpty()) {

                    invalidLead.put("reason", "Mandatory fields (clientName, phoneNo, primaryEmail) are missing");
                    invalidLeadsList.add(invalidLead);
                    continue; // Skip this lead as it is invalid
                }

                // Check for duplicates against both database and in-memory sets
                boolean isDuplicate = accountPhoneSet.contains(phoneNo) || accountEmailSet.contains(primaryEmail);

                // Update the sets
                accountPhoneSet.add(phoneNo);
                accountEmailSet.add(primaryEmail);

                // Set the tag for the lead
                lead.setTags(isDuplicate ? "Duplicate" : "");

                // Populate lead details
                lead.setClientName(clientName);
                lead.setPhoneNo(phoneNo);
                lead.setPrimaryEmail(primaryEmail);
                lead.setProjectName(getCellValue(row, columnIndexMap.get("projectname")));
                lead.setDescription(getCellValue(row, columnIndexMap.get("description")));
                lead.setSource(getCellValue(row, columnIndexMap.get("source")));
                lead.setStatus(getCellValue(row, columnIndexMap.get("status")));
                lead.setScope(getCellValue(row, columnIndexMap.get("scope")));
                lead.setBudget(getCellValue(row, columnIndexMap.get("budget")));
                lead.setFollowupDate(getCellValue(row, columnIndexMap.get("followupdate")));

                // Handle date parsing
                String startDateStr = getCellValue(row, columnIndexMap.get("startdate"));
                if (startDateStr != null && !startDateStr.isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    lead.setStartDate(dateFormat.parse(startDateStr));
                }

                // Set accountId for the lead
                lead.setAccountId(accountId);

                // Establish bidirectional relationship
                if (lead.getUsers() == null) {
                    lead.setUsers(new ArrayList<>()); // Initialize the list if it's null
                }
                lead.getUsers().add(user);

                if (user.getLeads() == null) {
                    user.setLeads(new ArrayList<>()); // Initialize the list if it's null
                }
                user.getLeads().add(lead);

                // Add the lead to the valid list
                validLeadsList.add(lead);
            }
        }

        // Save valid leads and update user-lead relationships
        leadsRepository.saveAll(validLeadsList);
        usersRepository.save(user);

        // Return the invalid leads list with reasons
        return invalidLeadsList;
    }


    private String getCellValue(Row row, Integer columnIndex) {
        if (columnIndex == null || row.getCell(columnIndex) == null) {
            return null;
        }

        Cell cell = row.getCell(columnIndex);
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }


    public Leads updateLeadDetails(Long leadId, Map<String, String> updates) {
        Leads lead = leadsRepository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead not found with ID: " + leadId));

        updates.forEach((key, value) -> {
            switch (key) {
                case "clientName":
                    lead.setClientName(value);
                    break;
                case "altClientName":
                    lead.setAltClientName(value);
                    break;
                case "phoneNo":
                    lead.setPhoneNo(value);
                    break;
                case "primaryEmail":
                    lead.setPrimaryEmail(value);
                    break;
                case "projectName":
                    lead.setProjectName(value);
                    break;
                case "description":
                    lead.setDescription(value);
                    break;
                case "scope":
                    lead.setScope(value);
                    break;
                case "source":
                    lead.setSource(value);
                    break;
                case "status":
                    lead.setStatus(value);
                    break;
                case "altEmail":
                    lead.setAltEmail(value);
                    break;
                case "altPhoneNo":
                    lead.setAltPhoneNo(value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        return leadsRepository.save(lead);
    }










}
