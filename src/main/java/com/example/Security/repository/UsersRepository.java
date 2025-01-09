package com.example.Security.repository;

import com.example.Security.entity.User;
import com.example.Security.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users,Long> {

    Optional<Users> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.isActive = true AND u.account.accountId = :accountId")
    long countActiveUsers(@Param("accountId") Long accountId);
    @Query("SELECT u FROM Users u WHERE u.isActive = true AND u.account.accountId = :accountId")
    List<Users> findActiveUsersList(@Param("accountId") Long accountId);

    @Query("SELECT u FROM Users u WHERE u.isActive = false AND u.account.accountId = :accountId")
    List<Users> findDeActiveUsersList(@Param("accountId") Long accountId);

}
