package com.monfauna.MonFaunaAPI.controller;

import com.google.gson.Gson;
import com.monfauna.MonFaunaAPI.dto.input.NewUserDTO;
import com.monfauna.MonFaunaAPI.dto.ResponseDTO;
import com.monfauna.MonFaunaAPI.dto.input.UpdatedUserDTO;
import com.monfauna.MonFaunaAPI.dto.UserDTO;
import com.monfauna.MonFaunaAPI.exception.BusinessRulesException;
import com.monfauna.MonFaunaAPI.exception.InvalidResourceException;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.model.User;
import com.monfauna.MonFaunaAPI.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

//caminho para acessar navegador @path
@Path("/users")
public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @GET
    @Produces("application/json")
    public Response index() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User u : users) {
            UserDTO userDTO = new UserDTO(u);
            userDTOS.add(userDTO);
        }
        return Response.ok(new Gson().toJson(userDTOS)).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response show(@PathParam("id") Integer id) {
        try {
            User user = userService.findById(id);
            UserDTO userDTO = new UserDTO(user);
            return Response.ok(new Gson().toJson(userDTO)).build();
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, e.getMessage());
            return Response.status(404).entity(new Gson().toJson(response)).build();
        }


    }

    @POST
    @Produces("application/json")
    public Response save(NewUserDTO newUser) {
        try {
            User user = userService.save(newUser.toModel());
            UserDTO userDTO = new UserDTO(user);
            return Response.status(201).entity(userDTO).build();
        } catch (InvalidResourceException e) {
            ResponseDTO response = new ResponseDTO(400, e.getMessage());
            return Response.status(400).entity(new Gson().toJson(response)).build();
        } catch (BusinessRulesException e) {
            ResponseDTO response = new ResponseDTO(409, e.getMessage());
            return Response.status(409).entity(new Gson().toJson(response)).build();
        }


    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public Response update(UpdatedUserDTO updatedUserDTO, @PathParam("id") Integer id) {
        try {
            User userUpdated = userService.update(id, updatedUserDTO.toModel());
            UserDTO userDTO = new UserDTO(userUpdated);
            return Response.ok(new Gson().toJson(userDTO)).build();
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
            userService.deleteById(id);
            return  Response.noContent().build(); //204 code
        } catch (NotFoundException e) {
            ResponseDTO response = new ResponseDTO(404, "User Not Found");
            return Response.status(404).entity(new Gson().toJson(response)).build();
        }
    }


}
