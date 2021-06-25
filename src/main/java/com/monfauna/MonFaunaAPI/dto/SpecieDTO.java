package com.monfauna.MonFaunaAPI.dto;

import com.monfauna.MonFaunaAPI.model.Specie;
import com.monfauna.MonFaunaAPI.model.SpecieType;

import java.time.LocalDateTime;

public class SpecieDTO {

    private Integer id;
    private String scientificName;
    private String commonName;
    private String createdAt;
    private String updatedAt;
    private String specieType;

    public SpecieDTO(Specie specie) {
        this.id = specie.getId();
        this.scientificName = specie.getScientificName();
        this.commonName = specie.getCommonName();
        this.createdAt = specie.getCreatedAt().toString();
        this.updatedAt = specie.getUpdatedAt().toString();
        this.specieType = specie.getSpecieType().getName();
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt.toString();
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt.toString();
    }

    public String getSpecieType() {
        return specieType;
    }

    public void setSpecieType(String specieType) {
        this.specieType = specieType;
    }
}
