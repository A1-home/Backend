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

    public AccountPreviewSetting getAccountPreviewSetting() {
        return accountPreviewSetting;
    }

    public void setAccountPreviewSetting(AccountPreviewSetting accountPreviewSetting) {
        this.accountPreviewSetting = accountPreviewSetting;
    }

    public PreviewColumn getPreviewColumns() {
        return previewColumns;
    }

    public void setPreviewColumns(PreviewColumn previewColumns) {
        this.previewColumns = previewColumns;
    }

    public PreviewDescription getPreviewDescriptions() {
        return previewDescriptions;
    }

    public void setPreviewDescriptions(PreviewDescription previewDescriptions) {
        this.previewDescriptions = previewDescriptions;
    }

    public PreviewSection getPreviewSections() {
        return previewSections;
    }

    public void setPreviewSections(PreviewSection previewSections) {
        this.previewSections = previewSections;
    }

    public List<TermsCondition> getTermsConditions() {
        return termsConditions;
    }

    public void setTermsConditions(List<TermsCondition> termsConditions) {
        this.termsConditions = termsConditions;
    }

    public List<AdditionalPage> getAdditionalPages() {
        return additionalPages;
    }

    public void setAdditionalPages(List<AdditionalPage> additionalPages) {
        this.additionalPages = additionalPages;
    }

    public AccountGST getAccountGST() {
        return accountGST;
    }

    public void setAccountGST(AccountGST accountGST) {
        this.accountGST = accountGST;
    }

    public List<PaymentPlan> getPaymentPlans() {
        return paymentPlans;
    }

    public void setPaymentPlans(List<PaymentPlan> paymentPlans) {
        this.paymentPlans = paymentPlans;
    }
}
