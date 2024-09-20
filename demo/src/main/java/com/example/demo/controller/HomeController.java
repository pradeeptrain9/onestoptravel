package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login(Authentication authentication) {
        // Check if the user is already authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Redirect to homepage or dashboard if already logged in
            return "redirect:/home";
        }
        // Render the login page if the user is not authenticated
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Serve the home.html template after login
    }




}
