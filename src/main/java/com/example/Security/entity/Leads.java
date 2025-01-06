package com.example.Security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"account", "users"}) // To prevent infinite recursion
public class Leads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leadId;  // Lead ID



    private String createdBy;  // Created By

    private String clientName;  // Client Name

    private String altClientName;  // Alternative Client Name

    private String phoneNo;  // Phone Number

    private String primaryEmail;  // Primary Email

    private String projectName;  // Project Name

    private String description;  // Description

    private String source;  // Source

    private String status;  // Status
    private String scope;

    private String altEmail;
    private String altPhoneNo;
    private Double budget;
    private String tags;


    private String followupDate;  // Follow-up Date

    @Temporal(TemporalType.DATE)
    private Date startDate;  // Start Date

    // Remarks stored as JSON (Employee ID, remark, timestamp)
//    @Column(columnDefinition = "json")
    private String remarks;  // Remarks (as JSON string)

    // AssignedTo stored as JSON (array of user IDs or usernames)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();  // Created At (current timestamp)

//    @JsonBackReference
    @JsonManagedReference
    @ManyToMany(mappedBy = "leads", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Users> users;


    @Override
    public String toString() {
        return "Leads{" +
                "leadId=" + leadId +
                ", createdBy='" + createdBy + '\'' +
                ", clientName='" + clientName + '\'' +
                ", altClientName='" + altClientName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", status='" + status + '\'' +
                ", scope='" + scope + '\'' +
                ", altEmail='" + altEmail + '\'' +
                ", altPhoneNo='" + altPhoneNo + '\'' +
                ", budget=" + budget +
                ", tags='" + tags + '\'' +
                ", startDate=" + startDate +
                ", followupDate=" + followupDate +
                ", remarks='" + remarks + '\'' +
                ", createdAt=" + createdAt +
                ", users=" + (users != null ? users.toString() : "No users assigned") +
                '}';
    }


    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }



    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAltClientName() {
        return altClientName;
    }

    public void setAltClientName(String altClientName) {
        this.altClientName = altClientName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAltEmail() {
        return altEmail;
    }

    public void setAltEmail(String altEmail) {
        this.altEmail = altEmail;
    }

    public String getAltPhoneNo() {
        return altPhoneNo;
    }

    public void setAltPhoneNo(String altPhoneNo) {
        this.altPhoneNo = altPhoneNo;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
