package com.monfauna.MonFaunaAPI.dto.input;

import com.monfauna.MonFaunaAPI.model.Project;

public class UpdatedProjectDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project toModel() {
        Project project = new Project();
        project.setName(name);
        return project;
    }
}
