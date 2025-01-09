package com.example.Security.entity;

import jakarta.persistence.*;

@Entity

public class LeadConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    private String name;

    private String colorCode;


    private Boolean isactive=true;

    private String type;

    public LeadConfig(Long id, Long accountId, String name, String colorCode, Boolean isactive, String type) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.colorCode = colorCode;
        this.isactive = isactive;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LeadConfig() {
    }
}
