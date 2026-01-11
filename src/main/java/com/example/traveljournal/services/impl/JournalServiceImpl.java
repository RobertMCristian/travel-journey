package com.example.traveljournal.services.impl;

import com.example.traveljournal.entities.JournalEntity;
import com.example.traveljournal.exceptions.ForbiddenException;
import com.example.traveljournal.exceptions.NotFoundException;
import com.example.traveljournal.model.CreateJournalRequest;
import com.example.traveljournal.model.JournalResponse;
import com.example.traveljournal.model.UpdateJournalRequest;
import com.example.traveljournal.repositories.JournalRepository;
import com.example.traveljournal.security.JwtAuthFilter;
import com.example.traveljournal.services.JournalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {
    private final JournalRepository journalRepository;

    public JournalServiceImpl(JournalRepository journalRepository){
        this.journalRepository = journalRepository;

    }
    private Long currentUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof JwtAuthFilter.JwtUserPrincipal p){
            return p.userId();
        }return null;
    }

    private JournalResponse toResponse(JournalEntity journalEntity){
        JournalResponse dto = new JournalResponse();
        dto.setId(journalEntity.getId());
        dto.setOwnerId(journalEntity.getOwnerId());
        dto.setTitle(journalEntity.getTitle());
        dto.setDescription(journalEntity.getDescription());
        dto.setStartDate(journalEntity.getStartDate());
        dto.setEndDate(journalEntity.getEndDate());
        dto.setCreatedAt(journalEntity.getCreatedAt());
        return dto;
    }

    @Override
    public JournalResponse create(CreateJournalRequest request){
        Long uid = currentUserId();
        if (uid==null) throw new ForbiddenException("unauthorized!");
        if(request.getOwnerId() != null && !uid.equals(request.getOwnerId())){
            throw new ForbiddenException("ownerId must match authenticated user");
        }

        JournalEntity journalEntity = new JournalEntity();
        journalEntity.setOwnerId(uid);
        journalEntity.setTitle(request.getTitle());
        journalEntity.setDescription(request.getDescription());
        journalEntity.setStartDate(request.getStartDate());
        journalEntity.setEndDate(request.getEndDate());

        return toResponse(journalRepository.save(journalEntity));
    }

    public List<JournalResponse> list(Long ownerId){
        Long uid = currentUserId();
        if(uid == null) throw new ForbiddenException("unauthorized!");
        Long effectiveOwner;
        if(ownerId == null){
            effectiveOwner = uid;
        } else effectiveOwner = ownerId;
        if (!uid.equals(effectiveOwner)){
            throw new ForbiddenException("not allowed");
        }

        return journalRepository.findByOwnerId(uid).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public JournalResponse get(Long journalId){
        Long uid = currentUserId();
        if (uid==null) throw new ForbiddenException("unauthorized!");
        JournalEntity journalEntity = journalRepository.findByIdAndOwnerId(journalId,uid)
                .orElseThrow(() -> new NotFoundException("journal not found "+ journalId));
        return toResponse(journalEntity);
    }

    @Override
    public JournalResponse update(Long journalId, UpdateJournalRequest request){
        Long uid = currentUserId();
        if (uid==null) throw new ForbiddenException("unauthorized!");

        JournalEntity journalEntity = journalRepository.findByIdAndOwnerId(journalId, uid)
                .orElseThrow(() -> new NotFoundException("journal not found: " + journalId));
        if(request.getTitle()!=null) journalEntity.setTitle(request.getTitle());
        if(request.getDescription()!=null) journalEntity.setDescription(request.getDescription());
        if(request.getStartDate()!=null) journalEntity.setStartDate(request.getStartDate());
        if(request.getEndDate()!=null) journalEntity.setEndDate(request.getEndDate());
        return toResponse(journalRepository.save(journalEntity));
    }

    @Override
    public void delete(Long journalId){
        Long uid = currentUserId();
        if (uid==null) throw new ForbiddenException("unauthorized!");
        JournalEntity journalEntity = journalRepository.findByIdAndOwnerId(journalId, uid)
                .orElseThrow(() -> new NotFoundException("journal not found: " + journalId));

        journalRepository.delete(journalEntity);
    }
}
