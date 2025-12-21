package com.example.traveljournal.services.impl;

import com.example.traveljournal.entities.UserEntity;
import com.example.traveljournal.entities.UserProfileEntity;
import com.example.traveljournal.exceptions.ConflictException;
import com.example.traveljournal.exceptions.NotFoundException;
import com.example.traveljournal.model.CreateUserRequest;
import com.example.traveljournal.model.UpdateUserRequest;
import com.example.traveljournal.model.UserResponse;
import com.example.traveljournal.repositories.UserRepository;
import com.example.traveljournal.services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private UserResponse toUserResponse(UserEntity user){
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found" + id));
        return toUserResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new ConflictException("email already in use");
        }
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getEmail());

        UserEntity saved = userRepository.save(user);
        return toUserResponse(saved);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request){
        UserEntity user = userRepository.findById(id).orElseThrow(()->new NotFoundException("user not found" + id));
        if(request.getName()!=null){
            user.setName(request.getName());
        }
        if(request.getSurname()!=null){
            user.setSurname(request.getSurname());
        }
        UserEntity saved = userRepository.save(user);
        return toUserResponse(saved);
    }

    @Override
    public void deleteUser(Long id){
        if (!userRepository.existsById(id)){
            throw new NotFoundException("user not found "+ id);
        }
        userRepository.deleteById(id);
    }
}
