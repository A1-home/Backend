package com.example.Security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Quotations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long accountId;


    private Long leadId; //author


    private String quotationName;


    private Double baseAmount;


    private Integer itemCount;


    private String leadName;

    private Long userId;

    private String authorName; // author name karna h




    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;




    private Boolean idDraft;

//    private Long templateId;

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
        this.updatedDate=new Date();
        this.idDraft=true;
        this.quotationName="Draft Quotation";
        this.baseAmount=0.0;
        this.itemCount=0;


    }

    // Getters and Setters
    public Long getId() {
        return id;
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

    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Boolean getIdDraft() {
        return idDraft;
    }

    public void setIdDraft(Boolean idDraft) {
        this.idDraft = idDraft;
    }

    public String getQuotationName() {
        return quotationName;
    }

    public void setQuotationName(String quotationName) {
        this.quotationName = quotationName;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        leadName = leadName;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public Double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }





    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        this.updatedDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}
