package com.example.Security.service;


import com.example.Security.entity.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtUtil {

    // Inject the secret key from application.properties or application.yml
    @Value("${jwt.secret}")
    private String secretKey;

    // Convert the secret key to a Key object
    private Key getSigningKey() {
//        System.out.println("Secret Key in JwtUtil: " + secretKey);

        return Keys.hmacShaKeyFor(secretKey.trim().getBytes(StandardCharsets.UTF_8));
    }


//     Method to generate the token
    public String generateToken(Users user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId",user.getUserId())
                .claim("userName",user.getUserName())
                .claim("accountId",user.getAccount().getAccountId())
                .claim("role", user.getRole()) // Include role
                .claim("email", user.getEmail()) // Include email
                .claim("accountName",user.getAccount().getCompanyName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken2(Users user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId",user.getUserId())
                .claim("userName",user.getUserName())
                .claim("accountId",user.getAccount().getAccountId())
                .claim("role", user.getRole()) // Include role
                .claim("email", user.getEmail()) // Include email
                .claim("accountName",user.getAccount().getCompanyName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Method to extract the email from the token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Method to validate the token (optional but recommended)
    public boolean validateToken(String token, String email) {
        String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // Method to check if the token is expired
    private boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }

    public Map<String, Object> extractUserDetails() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null; // Return null if the user is not authenticated
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> userDetails = (Map<String, Object>) authentication.getPrincipal();
        return userDetails;
    }
}


