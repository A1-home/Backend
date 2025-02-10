package com.example.Security.DTO;

public class QuotationLineItemDTO {

    private String area;

    private Long leadId;
    private String category;

    private Long accountId;
    private Long quotationId;
    private String subcategory;
    private String item;
    private String unitOfMeasurement;
    private Double rate;
    private Integer quantity;
    private String Description;
    private String specification;
    private String imageKey; // S3 object key
    private String author;

    private Boolean addToMainList;


    // Getters and Setters
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getAuthor() {
        return author;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getAddToMainList() {
        return addToMainList;
    }

    public void setAddToMainList(Boolean addToMainList) {
        this.addToMainList = addToMainList;
    }
}