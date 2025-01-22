package com.example.Security.repository;

import com.example.Security.entity.QuotationLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationsLineItemRepository extends JpaRepository<QuotationLineItem,Long> {
    @Query("SELECT q FROM QuotationLineItem q WHERE q.quotationId = :quotationId")
    List<QuotationLineItem> findByQuotationId(@Param("quotationId") Long quotationId);
}
