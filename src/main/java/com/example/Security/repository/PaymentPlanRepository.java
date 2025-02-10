package com.example.Security.repository;

import com.example.Security.entity.PaymentPlan;
import com.example.Security.entity.TermsCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan,Long> {

    List<PaymentPlan> findByAccountId(Long accountId);
    PaymentPlan findByAccountIdAndPaymentPlanName(Long accountId, String paymentPlanName);
}
