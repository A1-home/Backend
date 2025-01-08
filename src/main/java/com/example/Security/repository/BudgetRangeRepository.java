package com.example.Security.repository;

import com.example.Security.entity.BudgetRange;
import com.example.Security.entity.LeadStatusConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRangeRepository extends JpaRepository<BudgetRange,Long> {
    List<BudgetRange> findByAccountId(Long accountId);
}
