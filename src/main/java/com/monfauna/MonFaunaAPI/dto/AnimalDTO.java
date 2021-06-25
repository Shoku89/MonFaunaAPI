package com.monfauna.MonFaunaAPI.dto;

import com.monfauna.MonFaunaAPI.model.Animal;
import com.monfauna.MonFaunaAPI.model.AnimalMeasurement;
import com.monfauna.MonFaunaAPI.model.Location;
import com.monfauna.MonFaunaAPI.model.Specie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnimalDTO {

    private Integer id;
    private SpecieDTO specie;
    private String tag;
    private char sex;
    private String imageUrl;
    private String createdAt;
    private String updatedAt;
    private String registerDate;
    private Location location;
    private List<AnimalMeasurement> measurements;

    public AnimalDTO(Animal animal) {
        this.id = animal.getId();
        this.specie = new SpecieDTO(animal.getSpecie());
        this.tag = animal.getTag();
        this.sex = animal.getSex();
        this.imageUrl = animal.getImageUrl();
        this.createdAt = animal.getCreatedAt().toString();
        this.updatedAt = animal.getUpdatedAt().toString();
        this.registerDate = animal.getRegisterDate().toString();
        this.location = animal.getLocation();
        this.measurements = animal.getMeasurements();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SpecieDTO getSpecie() {
        return specie;
    }

    public void setSpecie(SpecieDTO specie) {
        this.specie = specie;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate.toString();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<AnimalMeasurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<AnimalMeasurement> measurements) {
        this.measurements = measurements;
    }
}
