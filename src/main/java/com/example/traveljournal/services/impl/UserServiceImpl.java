package com.example.traveljournal.services.impl;

import com.example.traveljournal.entities.UserEntity;
import com.example.traveljournal.exceptions.NotFoundException;
import com.example.traveljournal.model.UpdateUserRequest;
import com.example.traveljournal.model.UserResponse;
import com.example.traveljournal.repositories.UserRepository;
import com.example.traveljournal.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserResponse toUserResponse(UserEntity user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found" + id));
        return toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found " + id));

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getSurname() != null) {
            user.setSurname(request.getSurname());
        }

        UserEntity saved = userRepository.save(user);
        return toUserResponse(saved);
    }
}
