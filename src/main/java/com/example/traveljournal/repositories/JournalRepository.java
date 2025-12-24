package com.example.traveljournal.repositories;

import com.example.traveljournal.entities.JournalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<JournalEntity, Long> {

    List<JournalEntity> findByOwnerId(Long ownerId);

    long countByOwnerId(Long ownerId);
}
