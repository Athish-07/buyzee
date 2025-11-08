package com.buyzee.user_service.controller;


import com.buyzee.user_service.controller.dto.AuthResponse;
import com.buyzee.user_service.controller.dto.LoginRequest;
import com.buyzee.user_service.controller.dto.SignUpRequest;
import com.buyzee.user_service.model.User;
import com.buyzee.user_service.security.JwtService;
import com.buyzee.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserService userService,PasswordEncoder passwordEncoder,JwtService jwtService){
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest request){
        if ((request.getEmail()==null || request.getEmail().isBlank()) &&
                (request.getPhone()==null || request.getPhone().isBlank())){
            return ResponseEntity.badRequest().body(null);
        }

        User user=userService.userRegister(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getPassword()
        );


        String subject= user.getEmail() != null ? user.getEmail() : user.getPhone();

        String token = jwtService.generateToken(
                subject,
                Map.of("userId",user.getId(),
                        "role",user.getRole().name(),
                        "name",user.getName())
        );

        return ResponseEntity.ok(new AuthResponse(token,user.getRole().name(), user.getId(), user.getName()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        var userOpt = userService.findByEmailOrPhone(request.getEmail(), request.getPhone());
        if(userOpt.isEmpty())return ResponseEntity.status(401).build();

        User user =userOpt.get();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return ResponseEntity.status(401).build();
        }

        String subject = user.getEmail()!= null ? user.getEmail() : user.getPhone();
        String token =jwtService.generateToken(
                subject,
                Map.of("userid",user.getId(),
                        "role",user.getRole().name(),
                        "name",user.getName())
        );

        return ResponseEntity.ok(new AuthResponse(token,user.getRole().name(),user.getId(),user.getName()));
    }
}
