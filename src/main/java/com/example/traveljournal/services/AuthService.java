package com.example.traveljournal.services;

import com.example.traveljournal.model.AuthResponse;
import com.example.traveljournal.model.LoginRequest;
import com.example.traveljournal.model.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
