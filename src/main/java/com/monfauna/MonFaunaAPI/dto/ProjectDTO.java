package com.monfauna.MonFaunaAPI.dto;

import com.monfauna.MonFaunaAPI.model.Animal;
import com.monfauna.MonFaunaAPI.model.Project;
import com.monfauna.MonFaunaAPI.model.User;

import java.util.ArrayList;
import java.util.List;

public class ProjectDTO {

    private Integer id;
    private String name;
    private UserDTO owner;

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.owner = new UserDTO(project.getOwner());
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

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }
}
