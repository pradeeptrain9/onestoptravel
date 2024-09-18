package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void processOAuthPostLogin(String email, String name) {
        User existingUser = userRepository.findByUsername(email);

        if (existingUser == null) {
            User newUser = new User();
            newUser.setUsername(name);
            newUser.setEmail(email);  // No password for Google users


            userRepository.save(newUser);
        }
    }
}

