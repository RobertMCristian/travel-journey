package com.example.traveljournal.repositories;

import com.example.traveljournal.entities.JournalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<JournalEntity, Long> {

    List<JournalEntity> findByOwnerId(Long ownerId);
    Optional<JournalEntity> findByIdAndOwnerId(Long id, Long ownerId);
    boolean existsByIdAndOwnerId(Long id, Long ownerId);
}
