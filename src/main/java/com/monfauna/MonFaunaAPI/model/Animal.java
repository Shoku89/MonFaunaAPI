package com.monfauna.MonFaunaAPI.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Animal {

    private Integer id;
    private Specie specie;
    private String tag;
    private char sex;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate registerDate;
    private Location location;
    private List<AnimalMeasurement> measurements = new ArrayList<>();

    public Animal(){

    }

    public Animal(Integer id, String tag, Specie specie, char sex, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDate registerDate, Location location) {
        this.id = id;
        this.tag = tag;
        this.specie = specie;
        this.sex = sex;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.registerDate = registerDate;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
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

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
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

    public void addMeasurement(Double length, Double width, Double height, Double weight, String description){
        AnimalMeasurement animalMeasurement = new AnimalMeasurement();
        animalMeasurement.setHeight(height);
        animalMeasurement.setLength(length);
        animalMeasurement.setWidth(width);
        animalMeasurement.setWeight(weight);
        animalMeasurement.setDescription(description);

        this.measurements.add(animalMeasurement);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", specie=" + specie + '\'' +
                ", sex=" + sex +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", registerDate=" + registerDate +
                ", location=" + location +
                ", measurements=" + measurements +
                '}';
    }
}

