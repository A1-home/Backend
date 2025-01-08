package com.example.Security.DTO;

import lombok.Data;

@Data
public class FollowupDTO {
    private Long id;
    private String date;

    public FollowupDTO(Long id, String date) {
        this.id = id;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
