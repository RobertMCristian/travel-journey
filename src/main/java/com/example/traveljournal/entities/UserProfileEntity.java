package com.example.traveljournal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name= "user_profiles")
public class UserProfileEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "display_name", nullable = false)
    private String displayName;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public UserProfileEntity() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
