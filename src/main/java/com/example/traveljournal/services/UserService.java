package com.example.traveljournal.services;

import com.example.traveljournal.model.UpdateUserRequest;
import com.example.traveljournal.model.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse putUserByEmail(String email, UpdateUserRequest request);
    UserResponse putUserById(Long id, UpdateUserRequest request);
    UserResponse patchUserByEmail(String email, UpdateUserRequest request);
    UserResponse getUserByEmailPublic(String email);
    UserResponse getUserByEmail(String email);
    UserResponse patchUserById(Long id, UpdateUserRequest request);

}
