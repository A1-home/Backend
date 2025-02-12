// TermsConditonDetails.java
package com.example.Security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter@Setter
public class
TermsConditonDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String details;

    @ManyToOne
    @JoinColumn(name = "terms_condition_id", nullable = false) // Foreign key column referencing TermsCondition
    @JsonBackReference
    private TermsCondition termsCondition;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public TermsCondition getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(TermsCondition termsCondition) {
        this.termsCondition = termsCondition;
    }
}
