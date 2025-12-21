package com.example.traveljournal.controller;

import com.example.traveljournal.api.AuthApi;
import com.example.traveljournal.model.LoginRequest;
import com.example.traveljournal.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    @Override
    public ResponseEntity<UserResponse> loginPost(LoginRequest loginRequest) {
        return null;
    }
}
