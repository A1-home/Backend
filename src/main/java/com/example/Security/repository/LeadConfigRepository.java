package com.example.Security.repository;

import com.example.Security.entity.LeadConfig;
import com.example.Security.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface LeadConfigRepository extends JpaRepository<LeadConfig,Long> {
    List<LeadConfig> findByAccountIdAndType(Long accountId, String type);
}
