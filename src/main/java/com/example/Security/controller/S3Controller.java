package com.example.Security.controller;

import com.example.Security.service.JwtUtil;
import com.example.Security.service.S3Service;
import com.example.Security.service.S3Service.PresignedUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/awsBucket")
public class S3Controller {

    private final S3Service s3Service;

    @Autowired
    private final JwtUtil jwtUtil;

    public S3Controller(S3Service s3Service, JwtUtil jwtUtil) {
        this.s3Service = s3Service;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/generate-upload-url")
    public PresignedUrlResponse generateUploadUrl(@RequestParam String subFolder, @RequestParam String fileName) {

//        System.out.println(accountName+accountId+subFolder+fileName);
        Map<String, Object> userDetails = jwtUtil.extractUserDetails();

        // Extract accountName as String
        String accountName = (String) userDetails.get("accountName");

        // Extract accountId as Long and convert to String
        Long accountIdLong = (Long) userDetails.get("accountId");
        String accountId = String.valueOf(accountIdLong);

        // Generate and return the presigned URL
        return s3Service.generateUploadUrl(accountName, String.valueOf(accountId), subFolder, fileName);
    }


    @GetMapping("/generate-download-url")
    public String generateDownloadUrl(@RequestParam String objectKey) {
//        System.out.println("object Key" + objectKey);
        System.out.println(objectKey);
        return s3Service.generateDownloadUrl(objectKey);
    }
}
