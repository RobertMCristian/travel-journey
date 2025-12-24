package com.example.traveljournal.controller;

import com.example.traveljournal.api.UsersApi;
import com.example.traveljournal.model.UpdateUserRequest;
import com.example.traveljournal.model.UserResponse;
import com.example.traveljournal.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController implements UsersApi {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserResponse> usersIdGet(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<UserResponse> usersIdPatch(Long id, UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserRequest));
    }
}
