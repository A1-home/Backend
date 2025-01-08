package com.example.Security.repository;

import com.example.Security.entity.LeadStatusConfig;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadStatusRepository extends JpaRepository<LeadStatusConfig,Long> {

    List<LeadStatusConfig> findByAccountId(Long accountId);
}
