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
public class PreviewDescription {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private Long leadId = 0L;
    private Long quotationId = 0L;

    private Boolean area=true;
    private Boolean category=true;
    private Boolean subCategory=true;
    private Boolean itemName=true;
    private Boolean description=true;
    private Boolean specification=true;
    private Boolean image = false;

    public PreviewDescription(Long accountId, Long leadId, Long quotationId) {
        this.accountId = accountId;
        this.leadId = leadId;
        this.quotationId = quotationId;
    }

    public Long getId() {
        return id;
    }

    public PreviewDescription() {
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

    public Boolean getArea() {
        return area;
    }

    public void setArea(Boolean area) {
        this.area = area;
    }

    public Boolean getCategory() {
        return category;
    }

    public void setCategory(Boolean category) {
        this.category = category;
    }

    public Boolean getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Boolean subCategory) {
        this.subCategory = subCategory;
    }

    public Boolean getItemName() {
        return itemName;
    }

    public void setItemName(Boolean itemName) {
        this.itemName = itemName;
    }

    public Boolean getDescription() {
        return description;
    }

    public void setDescription(Boolean description) {
        this.description = description;
    }

    public Boolean getSpecification() {
        return specification;
    }

    public void setSpecification(Boolean specification) {
        this.specification = specification;
    }

    public Boolean getImage() {
        return image;
    }

    public void setImage(Boolean image) {
        this.image = image;
    }
}
