package com.example.traveljournal.services;

import com.example.traveljournal.model.CreateEntryRequest;
import com.example.traveljournal.model.EntryResponse;
import com.example.traveljournal.model.UpdateEntryRequest;

import java.util.List;

public interface EntryService {
    EntryResponse createEntry(Long journalId, CreateEntryRequest createEntryRequest);
    List<EntryResponse> listEntries(Long journalId);
    EntryResponse getEntry(Long journalId, Long entryId);
    EntryResponse updateEntry(Long journalId, Long entryId, UpdateEntryRequest updateEntryRequest);
    void deleteEntry(Long journalId, Long entryId);
}
