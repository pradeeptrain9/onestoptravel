package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
public class LocationController {

    // Return a list of locations as JSON
    @GetMapping("/getLocations")
    public List<String> getLocations() {
        // Simulate fetching locations from a database or an external service
        return Arrays.asList(
                "Mumbai",
                "Delhi",
                "Bangalore",
                "Hyderabad",
                "Ahmedabad",
                "Chennai",
                "Kolkata",
                "Pune",
                "Jaipur",
                "Surat",
                "Lucknow",
                "Kanpur",
                "Nagpur",
                "Indore",
                "Thane",
                "Bhopal",
                "Visakhapatnam",
                "Patna",
                "Vadodara",
                "Ghaziabad",
                "Ludhiana",
                "Agra",
                "Nashik",
                "Faridabad",
                "Meerut"
        );
    }
}
