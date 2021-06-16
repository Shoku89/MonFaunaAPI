package com.monfauna.MonFaunaAPI.dto.input;

import com.monfauna.MonFaunaAPI.dto.UserDTO;
import com.monfauna.MonFaunaAPI.model.Project;
import com.monfauna.MonFaunaAPI.model.User;

public class NewProjectDTO {

    private String name;
    private Integer ownerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Project toModel() {
        Project project = new Project();
        project.setName(name);
        User owner = new User();
        owner.setId(ownerId);
        project.setOwner(owner);
        return project;
    }

}
