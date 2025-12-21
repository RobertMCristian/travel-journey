package com.example.traveljournal.repositories;

import com.example.traveljournal.entities.LoginAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAttemptRepository extends JpaRepository<LoginAttemptEntity, Long> {
    long countByEmail(String email);
}
