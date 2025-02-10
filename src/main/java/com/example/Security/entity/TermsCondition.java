// TermsCondition.java
package com.example.Security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TermsCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    private String termsConditonName;

    @OneToMany(mappedBy = "termsCondition", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TermsConditonDetails> termsConditonDetails;





}
