package com.example.Security.controller;

import com.example.Security.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cont")

public class MyController {


    @GetMapping("/p")
    public String accessProtectedResource(@RequestHeader("Authorization") String token) {
     return "sonu";
    }

}
