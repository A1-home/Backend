package com.example.Security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId; // Account ID (Primary Key)

    //Representative name bhi hoga

    private String companyName; // Company Name

    private String representativeName;

    private String paymentId; // Payment ID

    private Boolean isActive =true;

    private Integer totalLicense; // Total License

    private String email; // Email




    private String phoneNumber; // Phone Number

    private String alias; // Alias

    private String address; // Address

    private String city; // City

    private String state; // State

    private String pinCode; // PinCode

    private String panNo; // PAN No

    @Column(length = 2000)
    private String bankDetails; // Bank Details (assuming it can be lengthy)

    private String logo; // Logo URL or path

    private String favicon; // Favicon URL or path

    private LocalDateTime createdAt; // Created at

    private String status; // Account status (e.g., pending, active, etc.)

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // Automatically set the creation date
        if (this.status == null) {
            this.status = "pending"; // Set default status if not provided
        }
    }
    public Long getAccountId() {
        return accountId;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getTotalLicense() {
        return totalLicense;
    }

    public void setTotalLicense(Integer totalLicense) {
        this.totalLicense = totalLicense;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
