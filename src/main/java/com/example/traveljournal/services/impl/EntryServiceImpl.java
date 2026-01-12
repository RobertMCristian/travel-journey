package com.example.traveljournal.services.impl;

import com.example.traveljournal.entities.EntryEntity;
import com.example.traveljournal.entities.JournalEntity;
import com.example.traveljournal.exceptions.ForbiddenException;
import com.example.traveljournal.exceptions.NotFoundException;
import com.example.traveljournal.exceptions.UnauthorizedException;
import com.example.traveljournal.model.CreateEntryRequest;
import com.example.traveljournal.model.EntryResponse;
import com.example.traveljournal.model.UpdateEntryRequest;
import com.example.traveljournal.repositories.EntryRepository;
import com.example.traveljournal.repositories.JournalRepository;
import com.example.traveljournal.security.JwtAuthFilter;
import com.example.traveljournal.services.EntryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {
    private final EntryRepository entryRepository;
    private final JournalRepository journalRepository;

    public EntryServiceImpl(EntryRepository entryRepository, JournalRepository journalRepository) {
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

    private EntryResponse toResponse(EntryEntity entryEntity){
        EntryResponse dto = new EntryResponse();
        dto.setId(entryEntity.getId());
        dto.setJournalId(entryEntity.getJournalId());
        dto.setEntryDate(entryEntity.getEntryDate());
        dto.setTitle(entryEntity.getTitle());
        dto.setContent(entryEntity.getContent());
        dto.setMood(entryEntity.getMood());
        dto.setCreatedAt(entryEntity.getCreatedAt());
        return dto;
    }

    @Override
    public EntryResponse createEntry(Long journalId, CreateEntryRequest request){
        checkJournalOwner(journalId);

        EntryEntity entryEntity = new EntryEntity();
        entryEntity.setJournalId(journalId);
        entryEntity.setEntryDate(request.getEntryDate());
        entryEntity.setTitle(request.getTitle());
        entryEntity.setContent(request.getContent());
        entryEntity.setMood(request.getMood());
        entryEntity.setCreatedAt(LocalDateTime.now());
        return  toResponse(entryRepository.save(entryEntity));
    }

    @Override
    public List<EntryResponse> listEntries(Long journalId){
        checkJournalOwner(journalId);
        return entryRepository.findAllByJournalId(journalId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public EntryResponse getEntry(Long journalId, Long entryId){
        checkJournalOwner(journalId);
        EntryEntity entryEntity = entryRepository.findByJournalIdAndId(journalId, entryId).orElseThrow(() -> new NotFoundException("entry not found "+entryId));
        return toResponse(entryEntity);
    }

    @Override
    public EntryResponse updateEntry(Long journalId, Long entryId, UpdateEntryRequest request){
        checkJournalOwner(journalId);
        EntryEntity entryEntity = entryRepository.findByJournalIdAndId(journalId, entryId).orElseThrow(() -> new NotFoundException("entry not found "+entryId));
        if(request.getEntryDate()!=null) entryEntity.setEntryDate(request.getEntryDate());
        if(request.getTitle()!=null) entryEntity.setTitle(request.getTitle());
        if(request.getContent()!=null) entryEntity.setContent(request.getContent());
        if(request.getMood()!=null) entryEntity.setMood(request.getMood());
        return  toResponse(entryRepository.save(entryEntity));
    }

    @Override
    public void deleteEntry(Long journalId, Long entryId){
        checkJournalOwner(journalId);
        EntryEntity entryEntity = entryRepository.findByJournalIdAndId(journalId, entryId).orElseThrow(() -> new NotFoundException("entry not found "+entryId));
        entryRepository.delete(entryEntity);
    }
}
