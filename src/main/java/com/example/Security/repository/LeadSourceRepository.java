package com.example.Security.repository;

import com.example.Security.entity.LeadSources;
import com.example.Security.entity.LeadStatusConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadSourceRepository extends JpaRepository<LeadSources,Long> {
    List<LeadSources> findByAccountId(Long accountId);
}
