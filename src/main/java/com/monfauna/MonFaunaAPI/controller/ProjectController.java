package com.monfauna.MonFaunaAPI.controller;


import com.google.gson.Gson;
import com.monfauna.MonFaunaAPI.dto.AnimalDTO;
import com.monfauna.MonFaunaAPI.dto.AnimalProjectDTO;
import com.monfauna.MonFaunaAPI.dto.ProjectDTO;
import com.monfauna.MonFaunaAPI.dto.ResponseDTO;
import com.monfauna.MonFaunaAPI.dto.input.InputAnimalDTO;
import com.monfauna.MonFaunaAPI.dto.input.NewProjectDTO;
import com.monfauna.MonFaunaAPI.dto.input.UpdateAnimalDTO;
import com.monfauna.MonFaunaAPI.dto.input.UpdatedProjectDTO;
import com.monfauna.MonFaunaAPI.exception.InvalidResourceException;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.model.Animal;
import com.monfauna.MonFaunaAPI.model.Project;
import com.monfauna.MonFaunaAPI.service.AnimalService;
import com.monfauna.MonFaunaAPI.service.ProjectService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/projects")
@Produces("application/json")
@Consumes("application/json")
public class ProjectController {

    private final ProjectService projectService;

    private final AnimalService animalService;

    public ProjectController() {
        this.projectService = new ProjectService();
        this.animalService = new AnimalService();
    }

    @GET
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
    public Response delete(@PathParam("id") Integer id) {
        try {
            projectService.deleteById(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        }
    }

    @GET
    @Path("/{id}/animals")
    public Response listAllAnimals(@PathParam("id") Integer idProject) {
        try {
            Project project = projectService.findById(idProject);
            List<Animal> animals = animalService.findAllByProject(project.getId());
            List<AnimalDTO> animalDTOS = new ArrayList<>();
            for (Animal a : animals) {
                AnimalDTO animalDTO = new AnimalDTO(a);
                animalDTOS.add(animalDTO);
            }
            return Response.ok(new Gson().toJson(animalDTOS)).build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        }

    }

    @GET
    @Path("{id}/animals/{idAnimal}")
    public Response getAnimalById(@PathParam("id") Integer id, @PathParam("idAnimal") Integer idAnimal) {
        try {
            Project project = projectService.findById(id);
            Animal animal = animalService.findById(idAnimal);
            AnimalProjectDTO animalDTO = new AnimalProjectDTO(animal, project);
            return Response.ok(new Gson().toJson(animalDTO)).build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        }

    }


    @POST
    @Path("{id}/animals")
    public  Response save(@PathParam("id") Integer id, InputAnimalDTO inputAnimalDTO) {
        try {
            Project project = projectService.findById(id);
            Animal animal = animalService.save(inputAnimalDTO.toModel(), project.getId());
            AnimalDTO animalDTO = new AnimalDTO(animal);
            return Response.status(201).entity(new Gson().toJson(animalDTO)).build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        }
    }


    @PUT
    @Path("{id}/animals/{idAnimal}")
    public Response update(UpdateAnimalDTO updateAnimalDTO,
                           @PathParam("id") Integer id,
                           @PathParam("idAnimal") Integer idAnimal) {
        try {
            projectService.findById(id);
            Animal animalUpdated = animalService.update(updateAnimalDTO.toModel(), idAnimal);
            AnimalDTO animalDTO = new AnimalDTO(animalUpdated);
            return Response.ok(new Gson().toJson(animalDTO)).build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        }
    }


}
