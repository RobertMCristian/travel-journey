package com.example.traveljournal.controller;

import com.example.traveljournal.api.EntriesApi;
import com.example.traveljournal.model.CreateEntryRequest;
import com.example.traveljournal.model.EntryResponse;
import com.example.traveljournal.model.UpdateEntryRequest;
import com.example.traveljournal.services.EntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntriesController implements EntriesApi {
    private final EntryService entryService;
    public EntriesController(EntryService entryService) {
        this.entryService = entryService;
    }

    @Override
    public ResponseEntity<EntryResponse> journalsJournalIdEntriesPost(Long journalId, CreateEntryRequest request){
        EntryResponse created = entryService.createEntry(journalId, request);
        return ResponseEntity.status(201).body(created);
    }

    @Override
    public ResponseEntity<List<EntryResponse>> journalsJournalIdEntriesGet(Long journalId){
        List<EntryResponse> list = entryService.listEntries(journalId);
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<EntryResponse> journalsJournalIdEntriesEntryIdGet(Long journalId, Long entryId){
        return ResponseEntity.ok(entryService.getEntry(journalId,entryId));
    }

    @Override
    public ResponseEntity<EntryResponse> journalsJournalIdEntriesEntryIdPatch(Long journalId, Long entryId, UpdateEntryRequest request){
        return ResponseEntity.ok(entryService.updateEntry(journalId, entryId, request));
    }

    @Override
    public ResponseEntity<Void> journalsJournalIdEntriesEntryIdDelete(Long journalId, Long entryId){
        entryService.deleteEntry(journalId,entryId);
        return ResponseEntity.noContent().build();
    }

}
