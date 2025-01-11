package com.example.Security.service;

import com.example.Security.DTO.FollowupDTO;
import com.example.Security.DTO.LeadDTO;
import com.example.Security.DTO.RemarkDTO;
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



//    public List<Map<String, String>> parseExcelFile(MultipartFile file, Users user) throws Exception {
//        List<Leads> validLeadsList = new ArrayList<>();
//        List<Map<String, String>> invalidLeadsList = new ArrayList<>();
//
//        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0);
//
//            // Read the header row and map column indices
//            Row headerRow = sheet.getRow(0);
//            Map<String, Integer> columnIndexMap = new HashMap<>();
//            for (Cell cell : headerRow) {
//                columnIndexMap.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
//            }
//
//            // Iterate through the rows to populate leads
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//                Leads lead = new Leads();
//                Map<String, String> invalidLead = new HashMap<>();
//
//                // Populate lead fields
//                String clientName = getCellValue(row, columnIndexMap.get("clientname"));
//                String phoneNo = getCellValue(row, columnIndexMap.get("phoneno"));
//                String primaryEmail = getCellValue(row, columnIndexMap.get("primaryemail"));
//
//                // Add all data to invalidLead map
//                invalidLead.put("row", String.valueOf(i));
//                invalidLead.put("clientName", clientName == null ? "" : clientName);
//                invalidLead.put("phoneNo", phoneNo == null ? "" : phoneNo);
//                invalidLead.put("primaryEmail", primaryEmail == null ? "" : primaryEmail);
//                invalidLead.put("projectName", getCellValue(row, columnIndexMap.get("projectname")));
//                invalidLead.put("description", getCellValue(row, columnIndexMap.get("description")));
//                invalidLead.put("source", getCellValue(row, columnIndexMap.get("source")));
//                invalidLead.put("status", getCellValue(row, columnIndexMap.get("status")));
//                invalidLead.put("scope", getCellValue(row, columnIndexMap.get("scope")));
//                invalidLead.put("budget", getCellValue(row, columnIndexMap.get("budget")));
//                invalidLead.put("followupDate", getCellValue(row, columnIndexMap.get("followupdate")));
//
//                // Validation for mandatory fields
//                if (clientName == null || clientName.isEmpty() ||
//                        phoneNo == null || phoneNo.isEmpty() ||
//                        primaryEmail == null || primaryEmail.isEmpty()) {
//
//                    invalidLead.put("reason", "Mandatory fields (clientName, phoneNo, primaryEmail) are missing");
//                    invalidLeadsList.add(invalidLead);
//                    continue;
//                }
//
//                lead.setClientName(clientName);
//                lead.setPhoneNo(phoneNo);
//                lead.setPrimaryEmail(primaryEmail);
//                lead.setProjectName(getCellValue(row, columnIndexMap.get("projectname")));
//                lead.setDescription(getCellValue(row, columnIndexMap.get("description")));
//                lead.setSource(getCellValue(row, columnIndexMap.get("source")));
//                lead.setStatus(getCellValue(row, columnIndexMap.get("status")));
//                lead.setScope(getCellValue(row, columnIndexMap.get("scope")));
//                lead.setBudget(getCellValue(row, columnIndexMap.get("budget")));
//                lead.setFollowupDate(getCellValue(row, columnIndexMap.get("followupdate")));
//
//                // Handle date parsing
//                String startDateStr = getCellValue(row, columnIndexMap.get("startdate"));
//                if (startDateStr != null && !startDateStr.isEmpty()) {
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                    lead.setStartDate(dateFormat.parse(startDateStr));
//                }
//
//                // Associate the user with the lead (Bidirectional relationship)
//                if (lead.getUsers() == null) {
//                    lead.setUsers(new ArrayList<>());
//                }
//                lead.getUsers().add(user);
//
//                if (user.getLeads() == null) {
//                    user.setLeads(new ArrayList<>());
//                }
//                user.getLeads().add(lead);
//
//                // Add the lead to the valid list
//                validLeadsList.add(lead);
//            }
//        }
//
//        // Save valid leads and update user-lead relationships
//        leadsRepository.saveAll(validLeadsList);
//        usersRepository.save(user);
//
//        // Return the invalid leads list with reasons
//        return invalidLeadsList;
//    }

    public List<Map<String, String>> parseExcelFile(MultipartFile file, Users user) throws Exception {
        List<Leads> validLeadsList = new ArrayList<>();
        List<Map<String, String>> invalidLeadsList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // Read the header row and map column indices
            Row headerRow = sheet.getRow(0);
            Map<String, Integer> columnIndexMap = new HashMap<>();
            for (Cell cell : headerRow) {
                columnIndexMap.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
            }

            // Iterate through the rows to populate leads
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
                invalidLead.put("projectName", getCellValue(row, columnIndexMap.get("projectname")));
                invalidLead.put("description", getCellValue(row, columnIndexMap.get("description")));
                invalidLead.put("source", getCellValue(row, columnIndexMap.get("source")));
                invalidLead.put("status", getCellValue(row, columnIndexMap.get("status")));
                invalidLead.put("scope", getCellValue(row, columnIndexMap.get("scope")));
                invalidLead.put("budget", getCellValue(row, columnIndexMap.get("budget")));
                invalidLead.put("followupDate", getCellValue(row, columnIndexMap.get("followupdate")));

                // Validation for mandatory fields
                if (clientName == null || clientName.isEmpty() ||
                        phoneNo == null || phoneNo.isEmpty() ||
                        primaryEmail == null || primaryEmail.isEmpty()) {

                    invalidLead.put("reason", "Mandatory fields (clientName, phoneNo, primaryEmail) are missing");
                    invalidLeadsList.add(invalidLead);
                    continue;
                }

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

                // Extract the accountId from the user and set it in the lead
                Long accountId = user.getAccount().getAccountId();  // Assuming 'getAccount()' returns the associated account
                lead.setAccountId(accountId);  // Set the accountId in the lead

                // Associate the user with the lead (Bidirectional relationship)
                if (lead.getUsers() == null) {
                    lead.setUsers(new ArrayList<>());
                }
                lead.getUsers().add(user);

                if (user.getLeads() == null) {
                    user.setLeads(new ArrayList<>());
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




    public LeadDTO findLeadById(Long leadId) {
        Leads lead = leadsRepository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead not found with ID: " + leadId));

        // Map entity to DTO
        return mapToDTO(lead);
    }

    private LeadDTO mapToDTO(Leads lead) {
        // Parse remarks
        List<RemarkDTO> parsedRemarks = lead.getRemarks() != null && !lead.getRemarks().isEmpty()
                ? Arrays.stream(lead.getRemarks().split(";"))
                .map(r -> {
                    String[] parts = r.split("\\|");
                    if (parts.length >= 4) { // Ensure at least 4 parts
                        // Combine any extra parts into the remark field
                        String remark = Arrays.stream(parts, 3, parts.length) // Start from index 3
                                .collect(Collectors.joining("|"));
                        return new RemarkDTO(
                                Long.parseLong(parts[0]), // userId
                                parts[1],                // userName
                                parts[2],                // date
                                remark                   // combined remark
                        );
                    } else {
                        throw new IllegalArgumentException("Invalid remark format: " + r);
                    }
                }).collect(Collectors.toList())
                : null;

        // Parse follow-up dates
        List<FollowupDTO> parsedFollowups = lead.getFollowupDate() != null && !lead.getFollowupDate().isEmpty()
                ? Arrays.stream(lead.getFollowupDate().split(";"))
                .map(f -> {
                    String[] parts = f.split("\\|");
                    if (parts.length >= 2) { // Ensure at least 2 parts
                        return new FollowupDTO(
                                Long.parseLong(parts[0]), // userId
                                parts[1]                 // follow-up date
                        );
                    } else {
                        throw new IllegalArgumentException("Invalid follow-up format: " + f);
                    }
                }).collect(Collectors.toList())
                : null;

        // Return the DTO
        return new LeadDTO(
                lead.getLeadId(),
                lead.getCreatedBy(),
                lead.getClientName(),
                lead.getAltClientName(),
                lead.getPhoneNo(),
                lead.getPrimaryEmail(),
                lead.getProjectName(),
                lead.getDescription(),
                lead.getSource(),
                lead.getStatus(),
                lead.getScope(),
                lead.getAltEmail(),
                lead.getAltPhoneNo(),
                lead.getBudget(),
                lead.getTags(),
                lead.getStartDate(),
                lead.getCreatedAt(),
                parsedRemarks,
                parsedFollowups
        );
    }




}
