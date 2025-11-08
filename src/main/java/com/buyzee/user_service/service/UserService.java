package com.buyzee.user_service.service;


import com.buyzee.user_service.model.Role;
import com.buyzee.user_service.model.User;
import com.buyzee.user_service.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
    Here what we are doing is getting access from userRepository class to use and give
    functionality to the method and passwordEncode to encrypt
     */

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* here is the main thing we are taking all inputs and preparing for validations
     */

    public User userRegister(String name, String email, String phone, String rawPassword){
        User user =User.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .password(passwordEncoder.encode(rawPassword))
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }

    /* giving functionalities to the method*/

    public Optional<User>findByEmailOrPhone(String email,String phone){
        if(email!=null && !email.isBlank()){
            return userRepository.findByEmail(email);
        }
        if(phone!=null && !phone.isBlank()){
            return userRepository.findByPhone(phone);
        }
        return Optional.empty();
    }



    public User getById(Long id){
        return userRepository.findById(id).orElseThrow();
    }
}
