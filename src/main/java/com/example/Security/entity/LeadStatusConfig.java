package com.example.Security.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "lead_status_config")
public class LeadStatusConfig  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "status_name", nullable = false)
    private String statusName;


    private Boolean isactive=false;
    private Boolean showcolorTip=false;

    @Column(name = "color_code", nullable = false)
    private String colorCode;

    public LeadStatusConfig(Long id, Long accountId, String statusName, Boolean isactive, Boolean showcolorTip, String colorCode, String tempColor) {
        this.id = id;
        this.accountId = accountId;
        this.statusName = statusName;
        this.isactive = isactive;
        this.showcolorTip = showcolorTip;
        this.colorCode = colorCode;
        this.tempColor = tempColor;
    }

    private String tempColor;

    public String getTempColor() {
        return tempColor;
    }

    public void setTempColor(String tempColor) {
        this.tempColor = tempColor;
    }

    public LeadStatusConfig(Long id, Long accountId, String statusName, Boolean isactive, Boolean showcolorTip, String colorCode) {
        this.id = id;
        this.accountId = accountId;
        this.statusName = statusName;
        this.isactive = isactive;
        this.showcolorTip = showcolorTip;
        this.colorCode = colorCode;
    }

    public LeadStatusConfig() {
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Boolean getShowcolorTip() {
        return showcolorTip;
    }

    public void setShowcolorTip(Boolean showcolorTip) {
        this.showcolorTip = showcolorTip;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}