package com.example.Security.repository;

import com.example.Security.entity.AccountGST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface AccountGSTRepository extends JpaRepository<AccountGST,Long> {


    AccountGST findByAccountId(Long accountId);
}
