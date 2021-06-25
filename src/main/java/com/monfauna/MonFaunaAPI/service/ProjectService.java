package com.monfauna.MonFaunaAPI.service;

import com.monfauna.MonFaunaAPI.dao.ProjectDao;
import com.monfauna.MonFaunaAPI.dao.impl.DaoFactory;
import com.monfauna.MonFaunaAPI.exception.InvalidResourceException;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.model.Project;
import com.monfauna.MonFaunaAPI.model.User;

import java.util.List;

public class ProjectService {

    private final ProjectDao projectDao;

    private final UserService userService;

    public ProjectService() {
        this.projectDao = DaoFactory.getProjectDao();
        this.userService = new UserService();
    }

    public List<Project> findAll() {
        return projectDao.findAll();
    }

    public Project findById(Integer id) throws NotFoundException {
        Project project = projectDao.findById(id);
        if (project == null) {
            throw new NotFoundException("Project Not Found");
        } else {
            return project;
        }

    }

    public Project createNewProject(Project project) throws NotFoundException, InvalidResourceException {
        project.validate();
        userService.findById(project.getOwner().getId());
        return projectDao.save(project);
    }

    public Project update(Integer id, Project projectUpdated) throws NotFoundException {
        Project project = this.findById(id);

        if (projectUpdated.getName() == null || projectUpdated.getName().isBlank()) {
            throw new InvalidResourceException("Name cannot be empty");
        } else {
            project.setName(projectUpdated.getName());
        }
        return projectDao.update(project);
    }

    public void deleteById(Integer id) throws NotFoundException {
        projectDao.deleteById(id);
    }
}
