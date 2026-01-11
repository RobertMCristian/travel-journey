package com.example.traveljournal.services.impl;

import com.example.traveljournal.entities.UserEntity;
import com.example.traveljournal.exceptions.NotFoundException;
import com.example.traveljournal.exceptions.ForbiddenException;
import com.example.traveljournal.model.UpdateUserRequest;
import com.example.traveljournal.model.UserResponse;
import com.example.traveljournal.repositories.UserRepository;
import com.example.traveljournal.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private Long currentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof com.example.traveljournal.security.JwtAuthFilter.JwtUserPrincipal p) {
            return p.userId();
        }
        return null;
    }

    private void ensureSelf(Long requestedUserId) {
        Long current = currentUserId();
        if (current == null || !current.equals(requestedUserId)) {
            throw new ForbiddenException("forbidden");
        }
    }

    @Override
    public UserResponse getUserById(Long id) {
        ensureSelf(id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found: " + id));
        return toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        ensureSelf(id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found: " + id));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getSurname() != null) user.setSurname(request.getSurname());

        UserEntity saved = userRepository.save(user);
        return toUserResponse(saved);
    }
}
