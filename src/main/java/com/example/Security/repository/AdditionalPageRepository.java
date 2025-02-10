package com.example.Security.repository;

import com.example.Security.entity.AdditionalPage;
import com.example.Security.entity.TermsCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AdditionalPageRepository  extends JpaRepository<AdditionalPage,Long> {

    List<AdditionalPage> findByAccountId(Long accountId);
}
