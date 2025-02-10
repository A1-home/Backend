package com.example.Security.repository;

import com.example.Security.entity.Quotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuotationsRepository extends JpaRepository<Quotations,Long> {

    @Query("SELECT q FROM Quotations q WHERE q.leadId = :leadId")
    List<Quotations> findByLeadId(@Param("leadId") Long leadId);


    @Query("SELECT q.id FROM Quotations q WHERE q.leadId = :leadId AND q.quotationName = 'Draft Quotation'")
    Optional<Long> findQuotationIdByLead(@Param("leadId") Long leadId);


}
