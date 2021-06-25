package com.monfauna.MonFaunaAPI.dto;

import com.monfauna.MonFaunaAPI.model.Animal;
import com.monfauna.MonFaunaAPI.model.Project;

public class AnimalProjectDTO extends AnimalDTO{

    private String projectName;

    public AnimalProjectDTO(Animal animal, Project project) {
        super(animal);
        this.projectName = project.getName();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
