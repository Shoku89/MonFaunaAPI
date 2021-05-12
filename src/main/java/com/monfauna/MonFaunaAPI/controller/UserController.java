package com.monfauna.MonFaunaAPI.controller;

import com.google.gson.Gson;
import com.monfauna.MonFaunaAPI.dao.UserDao;
import com.monfauna.MonFaunaAPI.dao.impl.DaoFactory;
import com.monfauna.MonFaunaAPI.dto.NewUserDTO;
import com.monfauna.MonFaunaAPI.dto.UserDTO;
import com.monfauna.MonFaunaAPI.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

//caminho para acessar navegador @path
@Path("/users")
public class UserController {

    @GET
    @Produces("application/json")
    public Response index() {
        UserDao userDao = DaoFactory.getUserDao();
        List<User> users = userDao.findAll();
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
        UserDao userDao = DaoFactory.getUserDao();
        User user = userDao.findById(id);
        UserDTO userDTO = new UserDTO(user);
        return  Response.ok(new Gson().toJson(userDTO)).build();
    }

    @POST
    @Produces("application/json")
    public Response save(NewUserDTO newUser) {
        UserDao userDao = DaoFactory.getUserDao();
        User user = userDao.save(newUser.toModel());
        UserDTO userDTO = new UserDTO(user);
        return  Response.status(201).entity(userDTO).build();

    }
}
