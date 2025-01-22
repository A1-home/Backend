package com.example.Security.repository;

import com.example.Security.entity.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface TemplatesRepository extends JpaRepository<Templates,Long> {
    List<Templates> findByAccountId(Long accountId);
    List<Templates> findByTemplateId(Integer templateId);
}
