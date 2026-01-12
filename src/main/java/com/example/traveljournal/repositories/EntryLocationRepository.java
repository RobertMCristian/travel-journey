package com.example.traveljournal.repositories;

import com.example.traveljournal.entities.EntryLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryLocationRepository extends JpaRepository<EntryLocationEntity, Long> {
}
