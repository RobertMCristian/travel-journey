package com.example.traveljournal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "entry_locations")
public class EntryLocationEntity {
    @Id
    @Column(name="entry_id")
    private Long entryId;

    @Column(length=100)
    private String country;

    @Column(length=100)
    private String city;

    @Column(length=100)
    private String placeName;

    private double latitude;
    private double longitude;

    public EntryLocationEntity() {
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
