//package com.example.Security.config;
//
//import com.example.Security.service.JwtAuthenticationFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless JWT-based APIs
//                .authorizeHttpRequests(auth -> auth
////                                .requestMatchers("/leads/**").permitAll()
//                        .requestMatchers("/auth/**","/accountPreviewSettings/**","/accountGST/**","/additionalPage/**","/termCondition/**","/paymentPlan/**","/awsBucket/**","/quotationLineItem/**","/quotationItem/**","/quotations/**","/cont/**","/users/**", "/check/**", "/budget/**", "/status/**", "/scope/**", "/source/**").permitAll() // Public endpoints
//                        .anyRequest().authenticated() // All other endpoints require authentication
//
//                )
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter
//
//        return http.build();
//    }
//}



package com.example.Security.config;

import com.example.Security.service.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless JWT-based APIs
                .authorizeHttpRequests(auth -> auth
                        // Permit all specific paths
                        .requestMatchers("/auth/**", "/accountPreviewSettings/**", "/accountGST/**",
                                "/additionalPage/**", "/termCondition/**",
                                 "/cont/**", "/users/**", "/check/**",
                                "/budget/**", "/status/**", "/scope/**", "/source/**").permitAll()
                        // Require authentication for other endpoints
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // Allow credentials (tokens)
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // Allow React frontend URL (replace if needed)
//        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("Authorization"); // Allow the Authorization header
        corsConfiguration.addAllowedHeader("Content-Type"); // Allow Content-Type header
        corsConfiguration.addAllowedMethod(HttpMethod.GET); // Allow GET method
        corsConfiguration.addAllowedMethod(HttpMethod.POST); // Allow POST method
        corsConfiguration.addAllowedMethod(HttpMethod.PUT); // Allow PUT method
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE); // Allow DELETE method

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Apply CORS to all paths
        return source;
    }
}
