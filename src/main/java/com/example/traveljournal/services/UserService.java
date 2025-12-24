package com.example.traveljournal.services;

import com.example.traveljournal.model.UpdateUserRequest;
import com.example.traveljournal.model.UserResponse;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest request);
}
