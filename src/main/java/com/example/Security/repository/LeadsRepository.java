package com.example.Security.repository;

import com.example.Security.entity.Leads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadsRepository extends JpaRepository<Leads,Long> {

    @Query("SELECT l FROM Leads l WHERE " + "LOWER(l.clientName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Leads> searchFlexibleLeadsByName(@Param("name") String name);
}
