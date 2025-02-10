package com.example.Security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${jwt.secret}")
    private String secretKey;





    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Retrieve the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token from the header
            String token = authorizationHeader.substring(7);

            try {
                // Parse the token and extract claims
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(getSigningKey()) // Implement getSigningKey() for your secret key
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                // Extract user details from claims
                String email = claims.getSubject();
                Long userId = claims.get("userId", Long.class);
                String userName = claims.get("userName", String.class);
                Long accountId = claims.get("accountId", Long.class);
                String role = claims.get("role", String.class);
                String accountName = claims.get("accountName", String.class);

                if (email != null) {
                    // Populate the authentication object with user details
                    Map<String, Object> userDetails = Map.of(
                            "userId", userId,
                            "userName", userName,
                            "accountId", accountId,
                            "role", role,
                            "email", email,
                            "accountName", accountName
                    );

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, new ArrayList<>()
                    );

                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Instead of throwing an error, inform the user to log in again
                logger.warn("Invalid or expired JWT token, please log in again.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set 401 status
                response.getWriter().write("Invalid or expired token, please log in again.");
                return; // End the request processing here
            }
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }



    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
