package com.example.Security.DTO;

import com.example.Security.entity.QuotationItemList;

import java.util.List;
import java.util.Map;

public class QuotationItemListResponseDTO {
    private List<QuotationItemList> quotationItemList;
    private Map<String, List<String>> catAndSubCat;
    private List<String> unitOfMeasurementList;

    public List<QuotationItemList> getQuotationItemList() {
        return quotationItemList;
    }

    public void setQuotationItemList(List<QuotationItemList> quotationItemList) {
        this.quotationItemList = quotationItemList;
    }

    public Map<String, List<String>> getCatAndSubCat() {
        return catAndSubCat;
    }

    public void setCatAndSubCat(Map<String, List<String>> catAndSubCat) {
        this.catAndSubCat = catAndSubCat;
    }

    public List<String> getUnitOfMeasurementList() {
        return unitOfMeasurementList;
    }

    public void setUnitOfMeasurementList(List<String> unitOfMeasurementList) {
        this.unitOfMeasurementList = unitOfMeasurementList;
    }
}
