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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getTermsConditonName() {
        return termsConditonName;
    }

    public void setTermsConditonName(String termsConditonName) {
        this.termsConditonName = termsConditonName;
    }

    public List<TermsConditonDetails> getTermsConditonDetails() {
        return termsConditonDetails;
    }

    public void setTermsConditonDetails(List<TermsConditonDetails> termsConditonDetails) {
        this.termsConditonDetails = termsConditonDetails;
    }
}
