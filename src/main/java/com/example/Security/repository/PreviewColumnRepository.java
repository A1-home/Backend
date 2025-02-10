package com.example.Security.repository;

import com.example.Security.entity.PaymentPlan;
import com.example.Security.entity.PreviewColumn;
import com.example.Security.entity.PreviewSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreviewColumnRepository extends JpaRepository<PreviewColumn,Long> {
    @Query("SELECT p FROM PreviewColumn p WHERE p.accountId = :accountId OR p.accountId = 0 ORDER BY CASE WHEN p.accountId = :accountId THEN 1 ELSE 2 END")
    PreviewColumn findByAccountId(@Param("accountId") Long accountId);

    PreviewColumn findByAccountIdAndLeadId(@Param("accountId") Long accountId, @Param("leadId") Long leadId);

}
