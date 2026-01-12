package com.example.traveljournal.services.impl;

import com.example.traveljournal.entities.UserEntity;
import com.example.traveljournal.exceptions.NotFoundException;
import com.example.traveljournal.exceptions.ForbiddenException;
import com.example.traveljournal.model.UpdateUserRequest;
import com.example.traveljournal.model.UserResponse;
import com.example.traveljournal.repositories.UserRepository;
import com.example.traveljournal.security.JwtAuthFilter;
import com.example.traveljournal.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private String currentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        Object principal = auth.getPrincipal();
        if(principal instanceof JwtAuthFilter.JwtUserPrincipal p) {
            return p.email();
        }if(principal instanceof String s){
            return s;
        } return null;
    }

    private void ensureSelfEmail(String requestedUserEmail) {
        String currentEmail = currentUserEmail();
        if(currentEmail == null || !currentEmail.equalsIgnoreCase(requestedUserEmail)) {
            throw new ForbiddenException("forbidden");
        }
    }

    @Override
    public UserResponse getUserByEmail(String email){
        ensureSelfEmail(email);
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found "+email));
    return toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers(){
        Long currentId = currentUserId();
        if(currentId == null) throw new ForbiddenException("missing auth");
        UserEntity user = userRepository.findById(currentId).orElseThrow(() -> new NotFoundException("user not found "+ currentId));
        return List.of(toUserResponse(user));
    }

    @Override
    public UserResponse putUserById(Long id, UpdateUserRequest request){
        ensureSelf(id);
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user not found "+ id));
        if(request.getName() == null) throw new IllegalArgumentException("name required");
        if(request.getSurname() == null) throw new IllegalArgumentException("surname required");
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        return toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserByEmailPublic(String email){
        return getUserByEmail(email);
    }

    @Override
    public UserResponse patchUserByEmail(String email, UpdateUserRequest request){
        ensureSelfEmail(email);
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found "+email));
        if(request.getName() != null) user.setName(request.getName());
        if(request.getSurname() != null) user.setSurname(request.getSurname());
        return toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse patchUserById(Long id, UpdateUserRequest request){
        ensureSelf(id);
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user not found "+id));
        if(request.getName() != null) user.setName(request.getName());
        if(request.getSurname() != null) user.setSurname(request.getSurname());
        return toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse putUserByEmail(String email, UpdateUserRequest request){
        ensureSelfEmail(email);
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found "+email));
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        if(request.getName() == null) user.setName(user.getName());
        if(request.getSurname() == null) user.setSurname(user.getSurname());
        return toUserResponse(userRepository.save(user));
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
