package com.monfauna.MonFaunaAPI.dto.input;

import com.monfauna.MonFaunaAPI.model.Animal;
import com.monfauna.MonFaunaAPI.model.AnimalMeasurement;
import com.monfauna.MonFaunaAPI.model.Specie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class UpdateAnimalDTO {

    private Integer specieId;
    private String tag;
    private char sex;
    private String imageUrl;
    private String registerDate; //yyyy-MM-dd


    public Integer getSpecieId() {
        return specieId;
    }

    public void setSpecieId(Integer specieId) {
        this.specieId = specieId;
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public Animal toModel() {
        Animal animal = new Animal();
        Specie specie = new Specie();
        specie.setId(specieId);
        animal.setSpecie(specie);
        animal.setTag(tag);
        animal.setSex(sex);
        animal.setImageUrl(imageUrl);
        animal.setRegisterDate(LocalDate.parse(registerDate, ofPattern("yyyy-MM-dd")));

        return animal;
    }

}
