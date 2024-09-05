package com.example.demo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login").permitAll() // Allow access to home and login pages
                                .anyRequest().authenticated() // All other requests require authentication
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login") // Custom login page
                                .defaultSuccessUrl("/home", true) // Redirect to home on successful login
                                .failureUrl("/login?error=true")
                );

        return http.build();
    }
}