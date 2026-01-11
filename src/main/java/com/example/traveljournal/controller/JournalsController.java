package com.example.traveljournal.controller;

import com.example.traveljournal.api.JournalsApi;
import com.example.traveljournal.model.CreateJournalRequest;
import com.example.traveljournal.model.JournalResponse;
import com.example.traveljournal.model.UpdateJournalRequest;
import com.example.traveljournal.services.JournalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JournalsController implements JournalsApi {
    private final JournalService journalService;

    public JournalsController(JournalService journalService) {
        this.journalService = journalService;
    }

    @Override
    public ResponseEntity<JournalResponse> journalsPost(CreateJournalRequest createJournalRequest) {
        JournalResponse created = journalService.create(createJournalRequest);
        return ResponseEntity.status(201).body(created);
    }


    @Override
    public ResponseEntity<List<JournalResponse>> journalsGet(Long ownerId, Long unused) {
        return ResponseEntity.ok(journalService.list(ownerId));
    }

    @Override
    public ResponseEntity<JournalResponse> journalsJournalIdGet(Long journalId) {
        return ResponseEntity.ok(journalService.get(journalId));
    }

    @Override
    public ResponseEntity<JournalResponse> journalsJournalIdPatch(Long journalId, UpdateJournalRequest updateJournalRequest) {
        return ResponseEntity.ok(journalService.update(journalId, updateJournalRequest));
    }

    @Override
    public ResponseEntity<Void> journalsJournalIdDelete(Long journalId) {
        journalService.delete(journalId);
        return ResponseEntity.noContent().build();
    }
}
