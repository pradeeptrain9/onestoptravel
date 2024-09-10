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
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers( "/css/styles.css","/login").permitAll() // Allow access to home and login pages
                                .anyRequest().authenticated() // All other requests require authentication
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login") // Custom login page
                                .defaultSuccessUrl("/home", true) // Redirect to home on successful login
                                .failureUrl("/login?error=true")
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Custom login page
                                .defaultSuccessUrl("/home", true) // Redirect to home on successful login
                                .failureUrl("/login?error=true") // Redirect to login page if login fails
                                .permitAll()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .maximumSessions(1) // Allow only one session per user
                                .expiredUrl("/login?expired=true") // Redirect to login if session expires
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout=true")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
