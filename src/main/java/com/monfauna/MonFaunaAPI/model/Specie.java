package com.monfauna.MonFaunaAPI.model;


import java.time.LocalDateTime;

public class Specie {

    private Integer id;
    private String scientificName;
    private String commonName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SpecieType specieType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SpecieType getSpecieType() {
        return specieType;
    }

    public void setSpecieType(SpecieType specieType) {
        this.specieType = specieType;
    }

    @Override
    public String toString() {
        return "Specie{" +
                "id=" + id +
                ", scientificName='" + scientificName + '\'' +
                ", commonName='" + commonName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", specieType=" + specieType +
                '}';
    }
}

