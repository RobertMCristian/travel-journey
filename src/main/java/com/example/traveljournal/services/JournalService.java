package com.example.traveljournal.services;

import com.example.traveljournal.model.CreateJournalRequest;
import com.example.traveljournal.model.JournalResponse;
import com.example.traveljournal.model.UpdateJournalRequest;
import com.example.traveljournal.repositories.JournalRepository;

import java.util.List;

public interface JournalService {
    JournalResponse create(CreateJournalRequest request);
    JournalResponse get(Long journalId);
    JournalResponse update(Long journalId, UpdateJournalRequest request);
    void delete(Long journalId);
    List<JournalResponse> list(Long ownerId);
}
