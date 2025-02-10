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
}
