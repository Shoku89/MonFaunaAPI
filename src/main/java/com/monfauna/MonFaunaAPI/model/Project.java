package com.monfauna.MonFaunaAPI.model;


import com.monfauna.MonFaunaAPI.exception.InvalidResourceException;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private Integer id;
    private String name;
    private User owner;
    private final List<User> collaborators = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new InvalidResourceException("Name can't be null");
        }
        if (owner == null) {
            throw new InvalidResourceException("Owner can't be null");
        }
        if (owner.getId() == null) {
            throw  new InvalidResourceException("Owner id can't be null");
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getCollaborators() {
        return collaborators;
    }


    public List<Animal> getAnimals() {
        return animals;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", collaborators=" + collaborators +
                ", animals=" + animals +
                '}';
    }
}

