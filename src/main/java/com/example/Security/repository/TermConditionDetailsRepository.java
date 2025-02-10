package com.example.Security.repository;

import com.example.Security.entity.TermsConditonDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermConditionDetailsRepository extends JpaRepository<TermsConditonDetails,Long> {
}
