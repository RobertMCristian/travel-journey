package com.example.traveljournal.services.impl;

import com.example.traveljournal.entities.EntryEntity;
import com.example.traveljournal.entities.EntryLocationEntity;
import com.example.traveljournal.entities.JournalEntity;
import com.example.traveljournal.exceptions.ForbiddenException;
import com.example.traveljournal.exceptions.NotFoundException;
import com.example.traveljournal.exceptions.UnauthorizedException;
import com.example.traveljournal.model.LocationResponse;
import com.example.traveljournal.model.PatchLocationRequest;
import com.example.traveljournal.model.UpsertLocationRequest;
import com.example.traveljournal.repositories.EntryLocationRepository;
import com.example.traveljournal.repositories.EntryRepository;
import com.example.traveljournal.repositories.JournalRepository;
import com.example.traveljournal.security.JwtAuthFilter;
import com.example.traveljournal.services.JournalService;
import com.example.traveljournal.services.LocationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {
    private final EntryLocationRepository entryLocationRepository;
    private final EntryRepository entryRepository;
    private final JournalRepository journalRepository;

    public LocationServiceImpl(EntryLocationRepository entryLocationRepository, EntryRepository entryRepository, JournalRepository journalRepository) {
        this.entryLocationRepository = entryLocationRepository;
        this.entryRepository = entryRepository;
        this.journalRepository = journalRepository;
    }
    private Long currentUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof JwtAuthFilter.JwtUserPrincipal p){
            return p.userId();
        }return null;
    }

    private void checkJournalOwner(Long journalId) {
        Long userId = currentUserId();
        if (userId == null) {
            throw new UnauthorizedException("missing authentication");
        }
        JournalEntity journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new NotFoundException("journal not found " + journalId));
        if (!journal.getOwnerId().equals(userId)) {
            throw new ForbiddenException("not your journal");
        }
    }

    private EntryEntity requireEntryInJournal(Long journalId, Long entryId) {
        checkJournalOwner(journalId);
        return entryRepository.findByJournalIdAndId(journalId, entryId).orElseThrow(() -> new NotFoundException("entry not found "+entryId));
    }

    private LocationResponse toResponse(EntryLocationEntity entryLocationEntity){
        LocationResponse dto = new LocationResponse();
        dto.setCountry(entryLocationEntity.getCountry());
        dto.setCity(entryLocationEntity.getCity());
        dto.setPlaceName(entryLocationEntity.getPlaceName());
        dto.setLatitude(entryLocationEntity.getLatitude());
        dto.setLongitude(entryLocationEntity.getLongitude());
        return dto;
    }

    @Override
    public LocationResponse upsertLocation(Long journalId, Long entryId, UpsertLocationRequest request){
        requireEntryInJournal(journalId, entryId);
        EntryLocationEntity entryLocationEntity = entryLocationRepository.findById(entryId).orElseGet(() ->{
            EntryLocationEntity entryLocation = new EntryLocationEntity();
            entryLocation.setEntryId(entryId);
            return entryLocation;
        });
        entryLocationEntity.setCountry(request.getCountry());
        entryLocationEntity.setCity(request.getCity());
        entryLocationEntity.setPlaceName(request.getPlaceName());
        entryLocationEntity.setLatitude(request.getLatitude());
        entryLocationEntity.setLongitude(request.getLongitude());
        return toResponse(entryLocationRepository.save(entryLocationEntity));
    }

    @Override
    public LocationResponse getLocation(Long journalId, Long entryId) {
        requireEntryInJournal(journalId, entryId);
        EntryLocationEntity entryLocationEntity = entryLocationRepository.findById(entryId).orElseThrow(() -> new NotFoundException("location for entry not found "+ entryId));
        return toResponse(entryLocationEntity);
    }

    @Override
    public LocationResponse patchLocation(Long journalId, Long entryId, PatchLocationRequest request){
        requireEntryInJournal(journalId, entryId);
        EntryLocationEntity entryLocationEntity = entryLocationRepository.findById(entryId).orElseThrow(() -> new NotFoundException("location for entry not found "+ entryId));
        if (request.getCountry()!=null){
            entryLocationEntity.setCountry(request.getCountry());
        };
        if (request.getCity()!=null){
            entryLocationEntity.setCity(request.getCity());
        };
        if (request.getPlaceName()!=null){
            entryLocationEntity.setPlaceName(request.getPlaceName());
        };
        if (request.getLatitude()!=null){
            entryLocationEntity.setLatitude(request.getLatitude());
        };
        if (request.getLongitude()!=null){
            entryLocationEntity.setLongitude(request.getLongitude());
        };
        return  toResponse(entryLocationRepository.save(entryLocationEntity));
    }
    @Override
    public void deleteLocation(Long journalId, Long entryId) {
        requireEntryInJournal(journalId, entryId);
        if(!entryLocationRepository.existsById(entryId)){
            throw new NotFoundException("entry not found "+entryId);
        }
        entryLocationRepository.deleteById(entryId);
    }
}
