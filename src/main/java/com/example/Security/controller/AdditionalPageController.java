package com.example.Security.controller;


import com.example.Security.entity.AdditionalPage;
import com.example.Security.repository.AdditionalPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/additionalPage")
public class AdditionalPageController {


    @Autowired
    private final AdditionalPageRepository additionalPageRepository;


    public AdditionalPageController(AdditionalPageRepository additionalPageRepository) {
        this.additionalPageRepository = additionalPageRepository;
    }



    @PostMapping("/add")
    public ResponseEntity<AdditionalPage> addAdditionalPage(@RequestBody AdditionalPage additionalPage) {
        AdditionalPage savedPage = additionalPageRepository.save(additionalPage);
        return ResponseEntity.ok(savedPage);
    }

    @GetMapping("/get")
    public List<AdditionalPage> getAdditonalPage(@RequestParam Long accountId)
    {
        return  additionalPageRepository.findByAccountId(accountId);
    }






}
