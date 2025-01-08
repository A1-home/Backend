package com.example.Security.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class LeadDTO {
    private Long leadId;
    private String createdBy;
    private String clientName;
    private String altClientName;
    private String phoneNo;
    private String primaryEmail;
    private String projectName;
    private String description;
    private String source;
    private String status;
    private String scope;
    private String altEmail;
    private String altPhoneNo;
    private String budget;
    private String tags;
    private Date startDate;
    private Date createdAt;

    private List<RemarkDTO> remarks;       // Parsed remarks
    private List<FollowupDTO> followups;  // Parsed follow-up dates

    public LeadDTO(Long leadId, String createdBy, String clientName, String altClientName, String phoneNo, String primaryEmail, String projectName, String description, String source, String status, String scope, String altEmail, String altPhoneNo, String budget, String tags, Date startDate, Date createdAt, List<RemarkDTO> remarks, List<FollowupDTO> followups) {
        this.leadId = leadId;
        this.createdBy = createdBy;
        this.clientName = clientName;
        this.altClientName = altClientName;
        this.phoneNo = phoneNo;
        this.primaryEmail = primaryEmail;
        this.projectName = projectName;
        this.description = description;
        this.source = source;
        this.status = status;
        this.scope = scope;
        this.altEmail = altEmail;
        this.altPhoneNo = altPhoneNo;
        this.budget = budget;
        this.tags = tags;
        this.startDate = startDate;
        this.createdAt = createdAt;
        this.remarks = remarks;
        this.followups = followups;
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

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<RemarkDTO> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<RemarkDTO> remarks) {
        this.remarks = remarks;
    }

    public List<FollowupDTO> getFollowups() {
        return followups;
    }

    public void setFollowups(List<FollowupDTO> followups) {
        this.followups = followups;
    }
}
