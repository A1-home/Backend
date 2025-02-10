package com.example.Security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
}
