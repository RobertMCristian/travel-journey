package com.example.traveljournal.controller;

import com.example.traveljournal.api.AuthApi;
import com.example.traveljournal.model.AuthResponse;
import com.example.traveljournal.model.LoginRequest;
import com.example.traveljournal.model.RegisterRequest;
import com.example.traveljournal.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<AuthResponse> authRegisterPost(RegisterRequest registerRequest) {
        AuthResponse response = authService.register(registerRequest);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<AuthResponse> authLoginPost(LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
