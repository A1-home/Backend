package com.example.Security.repository;

import com.example.Security.entity.User;
import com.example.Security.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users,Long> {

    Optional<Users> findByEmail(String email);

}
