package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Serve the login.html template
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Serve the home.html template after login
    }
}
