package com.buyzee.user_service.controller.dto;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String phone;
    private String password;
}
