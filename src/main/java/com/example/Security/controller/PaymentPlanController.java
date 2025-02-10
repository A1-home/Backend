package com.example.Security.controller;

import com.example.Security.entity.PaymentPlan;
import com.example.Security.entity.PlanDetail;
import com.example.Security.repository.PaymentPlanRepository;
import com.example.Security.repository.PlanDetailRepository;
import com.example.Security.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/paymentPlan")
public class PaymentPlanController {


    @Autowired
    private final PaymentPlanRepository paymentPlanRepository;

    @Autowired
    private final PlanDetailRepository planDetailRepository;

    @Autowired
    private  final JwtUtil jwtUtil;


    public PaymentPlanController(PaymentPlanRepository paymentPlanRepository, PlanDetailRepository planDetailRepository, JwtUtil jwtUtil) {
        this.paymentPlanRepository = paymentPlanRepository;
        this.planDetailRepository = planDetailRepository;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/add")
    public PaymentPlan createPaymentPlan(@RequestBody PaymentPlan paymentPlan) {
        // Ensure planDetails is not null
        if (paymentPlan.getPlanDetails() != null) {
            for (PlanDetail planDetail : paymentPlan.getPlanDetails()) {
                planDetail.setPaymentPlan(paymentPlan); // Set the reference to parent entity
            }
        }

        // Save paymentPlan, cascading will automatically save PlanDetails
        return paymentPlanRepository.save(paymentPlan);
    }

    @PutMapping("/addPlans/{accountId}/{paymentPlanName}")
    public PaymentPlan addPlanDetailsToExistingPaymentPlan(
            @PathVariable Long accountId,
            @PathVariable String paymentPlanName,
            @RequestBody List<PlanDetail> newPlanDetails) {

        // Find the existing PaymentPlan
        PaymentPlan existingPlan = paymentPlanRepository.findByAccountIdAndPaymentPlanName(accountId, paymentPlanName);
        if (existingPlan == null) {
            throw new RuntimeException("PaymentPlan not found with accountId " + accountId + " and paymentPlanName " + paymentPlanName);
        }

        // Add new PlanDetails to the existing PaymentPlan
        for (PlanDetail planDetail : newPlanDetails) {
            planDetail.setPaymentPlan(existingPlan);
            planDetailRepository.save(planDetail);
        }

        // Return the updated PaymentPlan with the new PlanDetails
        existingPlan.getPlanDetails().addAll(newPlanDetails); // Optionally add to the in-memory list
        return existingPlan;
    }


    @PutMapping("/editPlanDetails/{accountId}/{paymentPlanName}/{planDetailId}")
    public PlanDetail editPlanDetail(
            @PathVariable Long accountId,
            @PathVariable String paymentPlanName,
            @PathVariable Long planDetailId,
            @RequestBody PlanDetail updatedPlanDetail) {

        // Find the existing PaymentPlan
        PaymentPlan existingPlan = paymentPlanRepository.findByAccountIdAndPaymentPlanName(accountId, paymentPlanName);
        if (existingPlan == null) {
            throw new RuntimeException("PaymentPlan not found with accountId " + accountId + " and paymentPlanName " + paymentPlanName);
        }

        // Find the specific PlanDetail to edit
        PlanDetail planDetail = planDetailRepository.findById(planDetailId)
                .orElseThrow(() -> new RuntimeException("PlanDetail not found with id " + planDetailId));

        // Update the PlanDetail
        planDetail.setPlanName(updatedPlanDetail.getPlanName());
        planDetail.setPercentage(updatedPlanDetail.getPercentage());
        planDetail.setPaymentPlan(existingPlan);

        // Save the updated PlanDetail
        return planDetailRepository.save(planDetail);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentPlan>> getTermsConditionsByAccountId() {
        Map<String, Object> userDetails = jwtUtil.extractUserDetails();

        Long accountId = (Long) userDetails.get("accountId");
        List<PaymentPlan> paymentPlans = paymentPlanRepository.findByAccountId(accountId);
        if (paymentPlans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(paymentPlans, HttpStatus.OK);
        }
    }


    @DeleteMapping("/deletePlanDetails/{accountId}/{paymentPlanName}/{planDetailId}")
    public ResponseEntity<String> deletePlanDetail(
            @PathVariable Long accountId,
            @PathVariable String paymentPlanName,
            @PathVariable Long planDetailId) {

        // Find the existing PaymentPlan
        PaymentPlan existingPlan = paymentPlanRepository.findByAccountIdAndPaymentPlanName(accountId, paymentPlanName);
        if (existingPlan == null) {
            throw new RuntimeException("PaymentPlan not found with accountId " + accountId + " and paymentPlanName " + paymentPlanName);
        }

        // Find the specific PlanDetail to delete
        PlanDetail planDetail = planDetailRepository.findById(planDetailId)
                .orElseThrow(() -> new RuntimeException("PlanDetail not found with id " + planDetailId));

        // Delete the PlanDetail
        planDetailRepository.delete(planDetail);

        return ResponseEntity.ok("PlanDetail with id " + planDetailId + " successfully deleted");
    }



    @DeleteMapping("/deletePaymentPlan/{accountId}/{paymentPlanName}")
    public ResponseEntity<String> deletePaymentPlan(
            @PathVariable Long accountId,
            @PathVariable String paymentPlanName) {

        // Find the existing PaymentPlan
        PaymentPlan existingPlan = paymentPlanRepository.findByAccountIdAndPaymentPlanName(accountId, paymentPlanName);
        if (existingPlan == null) {
            throw new RuntimeException("PaymentPlan not found with accountId " + accountId + " and paymentPlanName " + paymentPlanName);
        }

        // Delete associated PlanDetails
        planDetailRepository.deleteAll(existingPlan.getPlanDetails());

        // Delete the PaymentPlan
        paymentPlanRepository.delete(existingPlan);

        return ResponseEntity.ok("PaymentPlan with accountId " + accountId + " and paymentPlanName " + paymentPlanName + " successfully deleted");
    }

}
