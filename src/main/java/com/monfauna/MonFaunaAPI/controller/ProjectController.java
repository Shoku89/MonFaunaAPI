package com.monfauna.MonFaunaAPI.controller;


import com.google.gson.Gson;
import com.monfauna.MonFaunaAPI.dto.ProjectDTO;
import com.monfauna.MonFaunaAPI.dto.ResponseDTO;
import com.monfauna.MonFaunaAPI.dto.input.NewProjectDTO;
import com.monfauna.MonFaunaAPI.dto.input.UpdatedProjectDTO;
import com.monfauna.MonFaunaAPI.exception.InvalidResourceException;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.model.Project;
import com.monfauna.MonFaunaAPI.service.ProjectService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController() {
        this.projectService = new ProjectService();
    }

    @GET
    @Produces("application/json")
    public Response findAllProjects() {
        List<Project> projects = projectService.findAll();
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        for (Project p : projects) {
            ProjectDTO projectDTO = new ProjectDTO(p);
            projectDTOList.add(projectDTO);
        }
        return Response.ok(new Gson().toJson(projectDTOList)).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findProjectById(@PathParam("id") Integer id) {
        try {
            Project project = projectService.findById(id);
            ProjectDTO projectDTO = new ProjectDTO(project);
            return Response.ok(new Gson().toJson(projectDTO)).build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        }

    }

    @POST
    @Produces("application/json")
    public Response createNewProject(NewProjectDTO newProject) {
        try {
            Project project = projectService.createNewProject(newProject.toModel());
            ProjectDTO projectDTO = new ProjectDTO(project);
            return Response.status(201).entity(projectDTO).build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        } catch (InvalidResourceException e) {
            ResponseDTO response = new ResponseDTO(400, e.getMessage());
            return  Response.status(400).entity(new Gson().toJson(response)).build();
        }

    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public Response update(UpdatedProjectDTO updatedProjectDTO, @PathParam("id") Integer id) {
        try {
            Project projectUpdated = projectService.update(id, updatedProjectDTO.toModel());
            ProjectDTO projectDTO = new ProjectDTO(projectUpdated);
            return Response.ok(new Gson().toJson(projectDTO)).build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        } catch (InvalidResourceException e) {
            ResponseDTO response = new ResponseDTO(400, e.getMessage());
            return Response.status(400).entity(new Gson().toJson(response)).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response delete(@PathParam("id") Integer id) {
        try {
            projectService.deleteById(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        }
    }





}
