package com.example.traveljournal.controller;

import com.example.traveljournal.api.LocationsApi;
import com.example.traveljournal.model.LocationResponse;
import com.example.traveljournal.model.PatchLocationRequest;
import com.example.traveljournal.model.UpsertLocationRequest;
import com.example.traveljournal.services.LocationService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationsController implements LocationsApi {
    private final LocationService locationService;
    public LocationsController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public ResponseEntity<LocationResponse> journalsJournalIdEntriesEntryIdLocationPut(Long journalId, Long entryId, UpsertLocationRequest upsertLocationRequest) {
        return ResponseEntity.ok(locationService.upsertLocation(journalId, entryId, upsertLocationRequest));
    }

    @Override
    public ResponseEntity<LocationResponse> journalsJournalIdEntriesEntryIdLocationGet(Long journalId, Long entryId) {
        return ResponseEntity.ok(locationService.getLocation(journalId, entryId));
    }

    @Override
    public ResponseEntity<LocationResponse> journalsJournalIdEntriesEntryIdLocationPatch(Long journalId, Long entryId, PatchLocationRequest patchLocationRequest) {
        return ResponseEntity.ok(locationService.patchLocation(journalId, entryId, patchLocationRequest));
    }

    @Override
    public ResponseEntity<Void> journalsJournalIdEntriesEntryIdLocationDelete(Long journalId, Long entryId) {
        locationService.deleteLocation(journalId, entryId);
        return ResponseEntity.ok().build();
    }
}
