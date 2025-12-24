package com.example.traveljournal.services.impl;

import com.example.traveljournal.entities.UserEntity;
import com.example.traveljournal.exceptions.ConflictException;
import com.example.traveljournal.exceptions.NotFoundException;
import com.example.traveljournal.model.AuthResponse;
import com.example.traveljournal.model.LoginRequest;
import com.example.traveljournal.model.RegisterRequest;
import com.example.traveljournal.model.UserResponse;
import com.example.traveljournal.repositories.UserRepository;
import com.example.traveljournal.services.AuthService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("email already in use");
        }

        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPasswordHash(hash(request.getPassword()));

        UserEntity saved = userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setToken(generateToken());
        response.setUser(toUserResponse(saved));
        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("invalid credentials"));

        String incomingHash = hash(request.getPassword());
        if (!incomingHash.equals(user.getPasswordHash())) {
            throw new NotFoundException("invalid credentials");
        }

        AuthResponse response = new AuthResponse();
        response.setToken(generateToken());
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

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String hash(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
