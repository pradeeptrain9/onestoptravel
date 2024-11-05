package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public void processOAuthPostLogin(String email, String name, String phoneNumber) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            User newUser = new User();
            newUser.setUsername(name);
            newUser.setEmail(email);
            newUser.setPhoneNumber(phoneNumber);


            userRepository.save(newUser);

            sendAcknowledgmentEmail(email, name);
        }
    }

    private void sendAcknowledgmentEmail(String email, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Thank you for registering with us!");
        message.setText("Hello " + name + ",\n\nThank you for connecting with us. We’ve successfully saved your contact information.\n\nBest regards,\nYOneStopTravel");

        mailSender.send(message);
    }
}

