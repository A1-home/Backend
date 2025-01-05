package com.example.Security.repository;

import com.example.Security.entity.Account;
import com.example.Security.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository  extends CrudRepository<Account,Long> {

    Optional<Account> findByEmail(String email);
}

