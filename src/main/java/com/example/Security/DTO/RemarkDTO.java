package com.example.Security.DTO;


import lombok.Data;

@Data
public class RemarkDTO {
    private Long userId;
    private String userName;
    private String date;
    private String remark;

    public RemarkDTO(Long userId, String userName, String date, String remark) {
        this.userId = userId;
        this.userName = userName;
        this.date = date;
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
