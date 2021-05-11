package com.monfauna.MonFaunaAPI.controller;

import com.google.gson.Gson;
import com.monfauna.MonFaunaAPI.dao.UserDao;
import com.monfauna.MonFaunaAPI.dao.impl.DaoFactory;
import com.monfauna.MonFaunaAPI.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

//caminho para acessar navegador @path
@Path("/users")
public class UserController {

    @GET
    @Produces("application/json")
    public Response index() {
        UserDao userDao = DaoFactory.getUserDao();
        List<User> users = userDao.findAll();
        return Response.ok(new Gson().toJson(users)).build();
    }
}
