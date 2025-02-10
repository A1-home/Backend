package com.example.Security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PreviewDescription {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private Long leadId = 0L;
    private Long quotationId = 0L;

    private Boolean area=true;
    private Boolean category=true;
    private Boolean subCategory=true;
    private Boolean itemName=true;
    private Boolean description=true;
    private Boolean specification=true;
    private Boolean image = false;

    public PreviewDescription(Long accountId, Long leadId, Long quotationId) {
        this.accountId = accountId;
        this.leadId = leadId;
        this.quotationId = quotationId;
    }
}
