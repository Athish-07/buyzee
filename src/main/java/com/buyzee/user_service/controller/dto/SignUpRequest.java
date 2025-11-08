package com.buyzee.user_service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*====== In Request or Response class we can use @Data Annotations =======*/

@Data
public class SignUpRequest {

    @NotBlank
    private String name;
    private String email;
    private String phone;
    @NotBlank
    private String password;
}
