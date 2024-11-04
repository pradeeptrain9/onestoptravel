package com.example.demo;

import com.example.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(UserService userService, ClientRegistrationRepository clientRegistrationRepository) {
        this.userService = userService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers( "/css/**","/login","/getLocations").permitAll()// Allow access to home and login pages
                                .requestMatchers("/home").authenticated()// Protected route for authenticated users
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
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // Custom logout URL
                                .logoutSuccessUrl("/login?logout=true") // Redirect to login page on successful logout
                                .invalidateHttpSession(true) // Invalidate session
                                .deleteCookies("JSESSIONID") // Delete session cookies
                                .permitAll()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .maximumSessions(1) // Allow only one session per user
                                .expiredUrl("/login?expired=true") // Redirect to login if session expires
                );

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            // Extract user information and store in the database

            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            String phoneNumber = oAuth2User.getAttribute("phoneNumber");
            System.out.print(email);
            userService.processOAuthPostLogin(email, name, phoneNumber);

            return oAuth2User;
        };
    }
}