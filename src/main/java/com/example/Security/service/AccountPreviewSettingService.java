package com.example.Security.service;

import com.example.Security.DTO.AccountSettingsResponse;
import com.example.Security.entity.*;
import com.example.Security.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountPreviewSettingService {

    @Autowired
    private AccountPreviewSettingRepository accountPreviewSettingRepository;

    @Autowired
    private PreviewColumnRepository previewColumnRepository;

    @Autowired
    private PreviewDescriptionRepository previewDescriptionRepository;

    @Autowired
    private PreviewSectionRepository previewSectionRepository;

    @Autowired
    private TermConditionRepository termsConditionRepository;

    @Autowired
    private AdditionalPageRepository additionalPageRepository;

    @Autowired
    private AccountGSTRepository accountGSTRepository;

    @Autowired
    private PaymentPlanRepository paymentPlanRepository;

    public AccountSettingsResponse getAccountSettings(Long accountId) {
        // Fetch data from repositories
        System.out.println(accountId);
        AccountPreviewSetting accountPreviewSetting = accountPreviewSettingRepository.findByAccountId(accountId);

        PreviewColumn previewColumns = previewColumnRepository.findByAccountId(accountId);
        PreviewDescription previewDescriptions = previewDescriptionRepository.findByAccountId(accountId);
        PreviewSection previewSections = previewSectionRepository.findByAccountId(accountId);
        List<TermsCondition> termsConditions = termsConditionRepository.findByAccountId(accountId);
        List<AdditionalPage> additionalPages = additionalPageRepository.findByAccountId(accountId);
        AccountGST accountGST = accountGSTRepository.findByAccountId(accountId);
        List<PaymentPlan> paymentPlans = paymentPlanRepository.findByAccountId(accountId);

        // No modification to id and accountId fields
        AccountSettingsResponse response = new AccountSettingsResponse();

        // Set accountPreviewSetting with its current fields (id and accountId are not modified)
        response.setAccountPreviewSetting(accountPreviewSetting);

        // Set the previewColumns, previewDescriptions, previewSections (without changing id and accountId)
        response.setPreviewColumns(previewColumns);
        response.setPreviewDescriptions(previewDescriptions);
        response.setPreviewSections(previewSections);
        response.setTermsConditions(termsConditions);
        response.setAdditionalPages(additionalPages);
        response.setAccountGST(accountGST);
        response.setPaymentPlans(paymentPlans);

        System.out.println(response);

        return response;
    }

    @Transactional
    public void updateAccountSettings(AccountSettingsResponse request) {
        AccountPreviewSetting accountPreviewSetting = request.getAccountPreviewSetting();
        Long accountId = accountPreviewSetting.getAccountId();

        // 🔹 1. Update Preview Columns
        PreviewColumn previewColumn = previewColumnRepository.findByAccountIdAndLeadId(accountId, 0L);
        if (previewColumn == null) {
            previewColumn = new PreviewColumn();
            previewColumn.setAccountId(accountId);
            previewColumn.setLeadId(0L);
            previewColumn.setQuotationId(0L);
        }
        previewColumn.setSerialNo(request.getPreviewColumns().getSerialNo());
        previewColumn.setDescription(request.getPreviewColumns().getDescription());
        previewColumn.setImage(request.getPreviewColumns().getImage());
        previewColumn.setDimensions(request.getPreviewColumns().getDimensions());
        previewColumn.setUom(request.getPreviewColumns().getUom());
        previewColumn.setRate(request.getPreviewColumns().getRate());
        previewColumn.setQuantity(request.getPreviewColumns().getQuantity());
        previewColumn.setTotalPrice(request.getPreviewColumns().getTotalPrice());
        previewColumn.setDiscountedPrice(request.getPreviewColumns().getDiscountedPrice());
        previewColumn.setRemarks(request.getPreviewColumns().getRemarks());
        previewColumnRepository.save(previewColumn);
        accountPreviewSetting.setColumnsToShowId(previewColumn.getId());

        // 🔹 2. Update Preview Description
        PreviewDescription previewDescription = previewDescriptionRepository.findByAccountIdAndLeadId(accountId, 0L);
        if (previewDescription == null) {
            previewDescription = new PreviewDescription();
            previewDescription.setAccountId(accountId);
            previewDescription.setLeadId(0L);
            previewDescription.setQuotationId(0L);
        }
        previewDescription.setArea(request.getPreviewDescriptions().getArea());
        previewDescription.setCategory(request.getPreviewDescriptions().getCategory());
        previewDescription.setSubCategory(request.getPreviewDescriptions().getSubCategory());
        previewDescription.setItemName(request.getPreviewDescriptions().getItemName());
        previewDescription.setDescription(request.getPreviewDescriptions().getDescription());
        previewDescription.setSpecification(request.getPreviewDescriptions().getSpecification());
        previewDescription.setImage(request.getPreviewDescriptions().getImage());
        previewDescriptionRepository.save(previewDescription);
        accountPreviewSetting.setDescriptionToShowId(previewDescription.getId());

        // 🔹 3. Update Preview Sections
        PreviewSection previewSection = previewSectionRepository.findByAccountIdAndLeadId(accountId, 0L);
        if (previewSection == null) {
            previewSection = new PreviewSection();
            previewSection.setAccountId(accountId);
            previewSection.setLeadId(0L);
            previewSection.setQuotationId(0L);
        }
        previewSection.setLogo(request.getPreviewSections().getLogo());
        previewSection.setCompanyDetails(request.getPreviewSections().getCompanyDetails());
        previewSection.setProjectDetails(request.getPreviewSections().getProjectDetails());
        previewSection.setHeader(request.getPreviewSections().getHeader());
        previewSection.setBankAccountDetails(request.getPreviewSections().getBankAccountDetails());
        previewSection.setTermsAndConditions(request.getPreviewSections().getTermsAndConditions());
        previewSection.setPaymentPlan(request.getPreviewSections().getPaymentPlan());
        previewSection.setDiscount(request.getPreviewSections().getDiscount());
        previewSection.setGst(request.getPreviewSections().getGst());
        previewSection.setAdditonalPage(request.getPreviewSections().getAdditonalPage());
        previewSection.setWaterMark(request.getPreviewSections().getWaterMark());
        previewSection.setAdditionalCharges(request.getPreviewSections().getAdditionalCharges());
        previewSectionRepository.save(previewSection);
        accountPreviewSetting.setRowsToShowId(previewSection.getId());

        // 🔹 4. Update Terms & Conditions
        accountPreviewSetting.setTermId(request.getAccountPreviewSetting().getTermId());

        // 🔹 5. Update Additional Pages (Multiple selection)
        accountPreviewSetting.setAdditionalPageIdsString(request.getAccountPreviewSetting().getAdditionalPageIdsString());

        // 🔹 6. Update Payment Plan
        accountPreviewSetting.setPaymentPlanId(request.getAccountPreviewSetting().getPaymentPlanId());

        // 🔹 7. Update Account GST
        AccountGST accountGST = request.getAccountGST();
        if (accountGST != null) {
            accountGSTRepository.save(accountGST);
        }

        // 🔹 8. Save Account Preview Setting
        accountPreviewSettingRepository.save(accountPreviewSetting);
    }
}

