package com.monfauna.MonFaunaAPI.dao;

import com.monfauna.MonFaunaAPI.model.Project;
import com.monfauna.MonFaunaAPI.model.User;

import java.util.List;

public interface ProjectDao extends Crud<Project> {
    //Modificador de acesso - tipo de retorno - nome do metodo - parametros de entrada
    List<User> findAllCollaboratorByProject(Integer idProject);
}

