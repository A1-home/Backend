package com.example.Security.repository;

import com.example.Security.entity.Leads;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadsRepository extends CrudRepository<Leads,Long> {

}
