package com.example.traveljournal.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "entries")
public class EntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="journal_id", nullable = false)
    private Long journalId;

    @Column(name="entry_Date", nullable = false)
    private LocalDate entryDate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String mood;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public EntryEntity() {}

    public Long getId() {
        return id;
    }

    public Long getJournalId() {
        return journalId;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getMood() {
        return mood;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
