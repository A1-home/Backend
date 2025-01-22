package com.example.Security.repository;

import com.example.Security.entity.QuotationItemList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationItemListRepository extends JpaRepository<QuotationItemList,Long> {
//    List<QuotationItemList> findByAccountId(Long accountId);
    List<QuotationItemList> findByAccountId(Long accountId);
}
