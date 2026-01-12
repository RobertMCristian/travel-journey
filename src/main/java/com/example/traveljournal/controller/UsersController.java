package com.example.traveljournal.controller;

import com.example.traveljournal.api.UsersApi;
import com.example.traveljournal.model.UpdateUserRequest;
import com.example.traveljournal.model.UserResponse;
import com.example.traveljournal.services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<UserResponse> usersIdPatch(Long id, @Valid UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserRequest));
    }

    @Override
    public ResponseEntity<List<UserResponse>> usersGet(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<UserResponse> usersEmailGet(String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @Override
    public ResponseEntity<UserResponse> usersEmailPatch(String email, UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.patchUserByEmail(email, updateUserRequest));
    }

    @Override
    public ResponseEntity<UserResponse> usersEmailPut(String email, UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.putUserByEmail(email, updateUserRequest));
    }

    @Override
    public ResponseEntity<UserResponse> usersIdPut(Long id, UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.putUserById(id, updateUserRequest));
    }

}
