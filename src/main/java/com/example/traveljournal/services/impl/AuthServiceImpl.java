package com.example.traveljournal.services.impl;

import com.example.traveljournal.entities.UserEntity;
import com.example.traveljournal.exceptions.ConflictException;
import com.example.traveljournal.exceptions.UnauthorizedException;
import com.example.traveljournal.model.AuthResponse;
import com.example.traveljournal.model.LoginRequest;
import com.example.traveljournal.model.RegisterRequest;
import com.example.traveljournal.model.UserResponse;
import com.example.traveljournal.repositories.UserRepository;
import com.example.traveljournal.security.JwtUtil;
import com.example.traveljournal.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("email in use");
        }

        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        UserEntity saved = userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setToken(jwtUtil.generateToken(saved.getId(), saved.getEmail()));
        response.setUser(toUserResponse(saved));
        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("invalid credentials, email not registered"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("invalid credentials, password is not correct");
        }

        AuthResponse response = new AuthResponse();
        response.setToken(jwtUtil.generateToken(user.getId(), user.getEmail()));
        response.setUser(toUserResponse(user));
        return response;
    }

    private UserResponse toUserResponse(UserEntity user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
