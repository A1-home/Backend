package com.example.Security.controller;

import com.example.Security.DTO.AccountSettingsResponse;
import com.example.Security.entity.AccountPreviewSetting;
import com.example.Security.repository.AccountPreviewSettingRepository;
import com.example.Security.service.AccountPreviewSettingService;
import com.example.Security.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accountPreviewSettings")
public class AccountPreviewSettingController {

    @Autowired
    private AccountPreviewSettingRepository accountPreviewSettingRepository;

    @Autowired
    private  AccountPreviewSettingService accountPreviewSettingService;

    @Autowired
    private JwtUtil jwtUtil;
    // Update or Create AccountPreviewSetting based on accountId
//    @PutMapping("/update")
//    public ResponseEntity<?> updateOrCreateAccountPreviewSetting(@RequestBody AccountPreviewSetting accountPreviewSetting) {
//        // Check if accountId is present
//        if (accountPreviewSetting.getAccountId() == null) {
//            return ResponseEntity.badRequest().body("Account ID must be provided.");
//        }
//
//        try {
//            // Convert additionalPageIds List<Long> to String (if provided in the request body)
//            if (accountPreviewSetting.getAdditionalPageIds() != null && !accountPreviewSetting.getAdditionalPageIds().isEmpty()) {
//                // Convert List<Long> to colon-delimited string
//                String additionalPageIdsString = accountPreviewSetting.getAdditionalPageIds().stream()
//                        .map(String::valueOf)
//                        .collect(Collectors.joining(","));
//                accountPreviewSetting.setAdditionalPageIdsString(additionalPageIdsString);
//            }
//
//            // Check if an existing AccountPreviewSetting with the given accountId exists
//            AccountPreviewSetting existingSetting = accountPreviewSettingRepository
//                    .findByAccountId(accountPreviewSetting.getAccountId());
//
//            if (existingSetting != null) {
//                // If a setting exists, check if leadId and quotationId are 0
//                if (existingSetting.getLeadId() == 0 && existingSetting.getQuotationId() == 0) {
//                    // Update the existing setting only with the provided values
//                    if (accountPreviewSetting.getTemplateName() != null) {
//                        existingSetting.setTemplateName(accountPreviewSetting.getTemplateName());
//                    }
//                    if (accountPreviewSetting.getRowsToShowId() != null) {
//                        existingSetting.setRowsToShowId(accountPreviewSetting.getRowsToShowId());
//                    }
//
//                    if (accountPreviewSetting.getColumnsToShowId() != null) {
//                        existingSetting.setColumnsToShowId(accountPreviewSetting.getColumnsToShowId());
//                    }
//                    if (accountPreviewSetting.getDescriptionToShowId() != null) {
//                        existingSetting.setDescriptionToShowId(accountPreviewSetting.getDescriptionToShowId());
//                    }
//                    if (accountPreviewSetting.getAdditionalPageIdsString() != null) {
//                        existingSetting.setAdditionalPageIdsString(accountPreviewSetting.getAdditionalPageIdsString());
//                    }
//                    // Save updated existing setting
//                    accountPreviewSettingRepository.save(existingSetting);
//                    return ResponseEntity.ok(existingSetting);
//                } else {
//                    return ResponseEntity.badRequest().body("Existing setting has leadId or quotationId not equal to 0.");
//                }
//            } else {
//                // If no existing setting found, create a new one
//                AccountPreviewSetting newSetting = accountPreviewSettingRepository.save(accountPreviewSetting);
//                return ResponseEntity.ok(newSetting);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("An error occurred while processing the AccountPreviewSetting.");
//        }
//    }



    @PutMapping("/update")
    public ResponseEntity<String> updateAccountSetting(@RequestBody AccountSettingsResponse accountSettingsResponse) {
        accountPreviewSettingService.updateAccountSettings(accountSettingsResponse);
        return ResponseEntity.ok("Account settings updated successfully");
    }


    @GetMapping("/getSettings")
    public ResponseEntity<?> getSettingsByAccountId() {
        try {

            Map<String, Object> userDetails = jwtUtil.extractUserDetails();
            if (userDetails == null) {
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }
            Long accountId = (Long) userDetails.get("accountId");
            AccountSettingsResponse response = accountPreviewSettingService.getAccountSettings(accountId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching settings.");
        }
    }
}
