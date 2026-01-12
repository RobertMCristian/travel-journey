package com.example.traveljournal.services;

import com.example.traveljournal.model.LocationResponse;
import com.example.traveljournal.model.PatchLocationRequest;
import com.example.traveljournal.model.UpsertLocationRequest;

public interface LocationService {
    LocationResponse upsertLocation(Long journalId, Long entryId, UpsertLocationRequest upsertLocationRequest);
    LocationResponse getLocation(Long journalId, Long entryId);
    LocationResponse patchLocation(Long journalId, Long entryId, PatchLocationRequest request);
    void deleteLocation(Long journalId, Long entryId);
}
