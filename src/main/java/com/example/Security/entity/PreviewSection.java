package com.example.Security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor

@Getter
@Setter
@ToString
public class PreviewSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    private Long leadId = 0L;
    private Long quotationId = 0L;
    private Boolean logo=true;
    private Boolean companyDetails=true;
    private Boolean projectDetails=true;
    private Boolean header=true;
    private Boolean bankAccountDetails=true;
    private Boolean termsAndConditions=true;
    private Boolean paymentPlan=true;

    private Boolean discount=false;
    private Boolean gst=false;
    private Boolean additonalPage=false;

    private Boolean waterMark=false;
    private Boolean additionalCharges=false;

    public PreviewSection(Long accountId, Long leadId, Long quotationId) {
        this.accountId = accountId;
        this.leadId = leadId;
        this.quotationId = quotationId;
    }

    public Long getId() {
        return id;
    }

    public PreviewSection() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Boolean getLogo() {
        return logo;
    }

    public void setLogo(Boolean logo) {
        this.logo = logo;
    }

    public Boolean getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(Boolean companyDetails) {
        this.companyDetails = companyDetails;
    }

    public Boolean getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(Boolean projectDetails) {
        this.projectDetails = projectDetails;
    }

    public Boolean getHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }

    public Boolean getBankAccountDetails() {
        return bankAccountDetails;
    }

    public void setBankAccountDetails(Boolean bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
    }

    public Boolean getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(Boolean termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public Boolean getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(Boolean paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public Boolean getGst() {
        return gst;
    }

    public void setGst(Boolean gst) {
        this.gst = gst;
    }

    public Boolean getAdditonalPage() {
        return additonalPage;
    }

    public void setAdditonalPage(Boolean additonalPage) {
        this.additonalPage = additonalPage;
    }

    public Boolean getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(Boolean waterMark) {
        this.waterMark = waterMark;
    }

    public Boolean getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(Boolean additionalCharges) {
        this.additionalCharges = additionalCharges;
    }
}
