package com.example.Security.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "payment_plan", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "payment_plan_name"}) // Ensures uniqueness
})
public class PaymentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "payment_plan_name", nullable = false)
    private String paymentPlanName;

    // One-to-many relationship with PlanDetail
    @OneToMany(mappedBy = "paymentPlan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PlanDetail> planDetails;
}
