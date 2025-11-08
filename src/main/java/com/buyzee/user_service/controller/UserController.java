package com.buyzee.user_service.controller;

import com.buyzee.user_service.model.User;
import com.buyzee.user_service.repo.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<User> me (Authentication auth){
        String subject =(String) auth.getPrincipal();

        return userRepository.findByEmail(subject)
                .or(()->
                        userRepository.findByPhone(subject))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
