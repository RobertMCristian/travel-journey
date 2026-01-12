package com.example.traveljournal.repositories;

import com.example.traveljournal.entities.EntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntryRepository extends JpaRepository<EntryEntity, Long> {
    List<EntryEntity> findAllByJournalId(Long journalId);
    Optional<EntryEntity> findByJournalIdAndId(Long journalId, Long id);
}
