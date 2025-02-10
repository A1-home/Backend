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
public class TermsConditonDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String details;

    @ManyToOne
    @JoinColumn(name = "terms_condition_id", nullable = false) // Foreign key column referencing TermsCondition
    @JsonBackReference
    private TermsCondition termsCondition;
}
