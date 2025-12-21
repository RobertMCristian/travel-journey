package com.example.traveljournal.controller;

import com.example.traveljournal.api.UsersApi;
import com.example.traveljournal.model.CreateUserRequest;
import com.example.traveljournal.model.UpdateUserRequest;
import com.example.traveljournal.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController implements UsersApi {

    @Override
    public ResponseEntity<List<UserResponse>> usersGet() {
        return ResponseEntity.ok(List.of());
    }

    @Override
    public ResponseEntity<Void> usersIdDelete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> usersIdGet(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> usersIdPut(Long id, UpdateUserRequest updateUserRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> usersPost(CreateUserRequest createUserRequest) {
        return null;
    }
}
