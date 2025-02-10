package com.example.Security.repository;

import com.example.Security.entity.PaymentPlan;
import com.example.Security.entity.PreviewDescription;
import com.example.Security.entity.PreviewSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreviewSectionRepository extends JpaRepository<PreviewSection,Long> {

    @Query("SELECT p FROM PreviewSection p WHERE p.accountId = :accountId OR p.accountId = 0 ORDER BY CASE WHEN p.accountId = :accountId THEN 1 ELSE 2 END")
    PreviewSection findByAccountId(@Param("accountId") Long accountId);

    PreviewSection findByAccountIdAndLeadId(@Param("accountId") Long accountId,@Param("leadId") Long leadId);
}
