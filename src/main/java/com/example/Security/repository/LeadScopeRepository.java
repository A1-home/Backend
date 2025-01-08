package com.example.Security.repository;

import com.example.Security.entity.LeadScopes;
import com.example.Security.entity.LeadStatusConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadScopeRepository extends JpaRepository<LeadScopes,Long> {
    List<LeadScopes> findByAccountId(Long accountId);
}
