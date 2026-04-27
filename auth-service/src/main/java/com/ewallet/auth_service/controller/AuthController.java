package com.ewallet.auth_service.controller;

import com.ewallet.auth_service.dto.AuthResponse;
import com.ewallet.auth_service.dto.LoginRequest;
import com.ewallet.auth_service.dto.RegisterRequest;
import com.ewallet.auth_service.entity.User;
import com.ewallet.auth_service.repository.UserRepository;
import com.ewallet.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3003")

public class AuthController {

    @Autowired
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("User with this Email ID already exists");
        }
        AuthResponse response=authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (!userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
        return ResponseEntity.ok(authService.login(request));
    }
}

