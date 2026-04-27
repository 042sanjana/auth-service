package com.ewallet.auth_service.service;

import com.ewallet.auth_service.dto.AuthResponse;
import com.ewallet.auth_service.dto.LoginRequest;
import com.ewallet.auth_service.dto.RegisterRequest;
import com.ewallet.auth_service.entity.User;
import com.ewallet.auth_service.event.UserEvent;
import com.ewallet.auth_service.repository.UserRepository;
import com.ewallet.auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.security.Principal;


@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RabbitTemplate rabbitTemplate;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.USER)
                .build();

        userRepository.save(user);

        UserEvent event = new UserEvent(
                user.getId(),
                request.getEmail(),
                request.getFullName(),
                request.getPhoneNumber()
        );

        try {
            rabbitTemplate.convertAndSend("user.exchange", "user.routing", event);
            System.out.println("Message sent to RabbitMQ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }


}