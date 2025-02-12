package com.example.Security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity

@Getter
@Setter
@ToString
public class PreviewColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private Long quotationId = 0L;
    private Long leadId = 0L;
    private Boolean serialNo=true;
    private Boolean description=true;
    private Boolean image=true;
    private Boolean dimensions=false;
    private Boolean uom=true;
    private Boolean rate=true;
    private Boolean quantity=true;
    private Boolean totalPrice=true;
    private Boolean discountedPrice=false;
    private Boolean remarks=true;


    public PreviewColumn( Long accountId, Long quotationId, Long leadId) {

        this.accountId = accountId;
        this.quotationId = quotationId;
        this.leadId = leadId;
    }

    public PreviewColumn() {
    }

    public PreviewColumn(Long id, Long accountId, Long quotationId, Long leadId, Boolean serialNo, Boolean description, Boolean image, Boolean dimensions, Boolean uom, Boolean rate, Boolean quantity, Boolean totalPrice, Boolean discountedPrice, Boolean remarks) {
        this.id = id;
        this.accountId = accountId;
        this.quotationId = quotationId;
        this.leadId = leadId;
        this.serialNo = serialNo;
        this.description = description;
        this.image = image;
        this.dimensions = dimensions;
        this.uom = uom;
        this.rate = rate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.discountedPrice = discountedPrice;
        this.remarks = remarks;
    }

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

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public Boolean getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Boolean serialNo) {
        this.serialNo = serialNo;
    }

    public Boolean getDescription() {
        return description;
    }

    public void setDescription(Boolean description) {
        this.description = description;
    }

    public Boolean getImage() {
        return image;
    }

    public void setImage(Boolean image) {
        this.image = image;
    }

    public Boolean getDimensions() {
        return dimensions;
    }

    public void setDimensions(Boolean dimensions) {
        this.dimensions = dimensions;
    }

    public Boolean getUom() {
        return uom;
    }

    public void setUom(Boolean uom) {
        this.uom = uom;
    }

    public Boolean getRate() {
        return rate;
    }

    public void setRate(Boolean rate) {
        this.rate = rate;
    }

    public Boolean getQuantity() {
        return quantity;
    }

    public void setQuantity(Boolean quantity) {
        this.quantity = quantity;
    }

    public Boolean getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Boolean totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Boolean discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Boolean getRemarks() {
        return remarks;
    }

    public void setRemarks(Boolean remarks) {
        this.remarks = remarks;
    }
}
