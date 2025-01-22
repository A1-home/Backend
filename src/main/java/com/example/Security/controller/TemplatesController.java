package com.example.Security.controller;

import com.example.Security.entity.Templates;
import com.example.Security.repository.TemplatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/templates")
public class TemplatesController {

    @Autowired
    private TemplatesRepository templatesRepository;

    // Create a new Template
    @PostMapping
    public ResponseEntity<Templates> createTemplate(@RequestBody Templates template) {
        Templates savedTemplate = templatesRepository.save(template);
        return ResponseEntity.ok(savedTemplate);
    }

    // Get all Templates
    @GetMapping("/findAll/{accountId}")
    public ResponseEntity<List<Templates>> getAllTemplates(@PathVariable Long accountId) {
        List<Templates> templates;
        if (accountId != null) {
            templates = templatesRepository.findByAccountId(accountId);
        } else {
            templates = templatesRepository.findAll();
        }
        return ResponseEntity.ok(templates);
    }
    // Get a Template by ID
    @GetMapping("/{templateId}")
    public ResponseEntity<List<Templates>> getTemplatesByTemplateId(@PathVariable Integer templateId) {
        List<Templates> templates = templatesRepository.findByTemplateId(templateId);

        if (templates.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(templates);
        }
    }


    // Update a Template
    @PutMapping("/{id}")
    public ResponseEntity<Templates> updateTemplate(@PathVariable Long id, @RequestBody Templates updatedTemplate) {
        Optional<Templates> templateOptional = templatesRepository.findById(id);
        if (templateOptional.isPresent()) {
            Templates existingTemplate = templateOptional.get();
            existingTemplate.setTemplateId(updatedTemplate.getTemplateId());
            existingTemplate.setTemplateName(updatedTemplate.getTemplateName());
            existingTemplate.setSpaceName(updatedTemplate.getSpaceName());
            existingTemplate.setAreaType(updatedTemplate.getAreaType());
            existingTemplate.setHeight(updatedTemplate.getHeight());
            existingTemplate.setWidth(updatedTemplate.getWidth());
            existingTemplate.setLength(updatedTemplate.getLength());
            existingTemplate.setUnit(updatedTemplate.getUnit());

            Templates savedTemplate = templatesRepository.save(existingTemplate);
            return ResponseEntity.ok(savedTemplate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Template
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        if (templatesRepository.existsById(id)) {
            templatesRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
