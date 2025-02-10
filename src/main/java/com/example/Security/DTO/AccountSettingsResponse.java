package com.example.Security.DTO;

import com.example.Security.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSettingsResponse {

    private AccountPreviewSetting accountPreviewSetting;
    private PreviewColumn previewColumns;
    private PreviewDescription previewDescriptions;
    private PreviewSection previewSections;
    private List<TermsCondition> termsConditions;
    private List<AdditionalPage> additionalPages;
    private AccountGST accountGST;
    private List<PaymentPlan> paymentPlans;


}
