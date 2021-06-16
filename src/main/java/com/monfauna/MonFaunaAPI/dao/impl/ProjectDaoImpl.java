package com.monfauna.MonFaunaAPI.dao.impl;


import com.monfauna.MonFaunaAPI.dao.AnimalDao;
import com.monfauna.MonFaunaAPI.dao.ProjectDao;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.infrastructure.Database;
import com.monfauna.MonFaunaAPI.model.Project;
import com.monfauna.MonFaunaAPI.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {

    private final Connection conn;
    private  final AnimalDao animalDao;


    public ProjectDaoImpl(Connection conn, AnimalDao animalDao ) {
        this.conn = conn;
        this.animalDao = animalDao;
    }

    @Override
    public Project save(Project project) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "INSERT INTO project (name, owner_user_id) VALUES (?, ?);";

        try {

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, project.getName());
            ps.setInt(2, project.getOwner().getId());

            int rowsAffected = ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rowsAffected != 0 && rs.next()) {
                Integer idGenerated = rs.getInt(1);
                return this.findById(idGenerated);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            Database.closeResultSet(rs);
            Database.closePreparedStatement(ps);
        }


        return null;
    }

    @Override
    public List<Project> findAll() {

        List<Project> projects = new ArrayList<>();
        ResultSet rs = null;

        String sql = "SELECT project.*, " +
                "user.name as nameUser, user.email, user.admin, user.created_at as userCreatedAt, user.updated_at as userUpdatedAt " +
                "FROM project " +
                "LEFT JOIN user ON user.id = project.owner_user_id;";

        try {
            rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                Project project = getInstanceProject(rs);
                project.setOwner(getInstanceUser(rs));
                project.getCollaborators().addAll(findAllCollaboratorByProject(project.getId()));
                project.getAnimals().addAll(animalDao.findAllByProject(project.getId()));
                projects.add(project);
            }

            return projects;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return null;
    }


    @Override
    public Project findById(Integer id) {

        PreparedStatement ps = null;
        ResultSet rs = null;


        String sql = "SELECT project.*, " +
                "user.name as nameUser, user.email, user.admin, user.created_at as userCreatedAt, user.updated_at as userUpdatedAt " +
                "FROM project " +
                "LEFT JOIN user ON user.id = project.owner_user_id " +
                "WHERE project.id = ?";

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Project project = getInstanceProject(rs);
                project.setOwner(getInstanceUser(rs));
                project.getCollaborators().addAll(findAllCollaboratorByProject(project.getId()));
                project.getAnimals().addAll(animalDao.findAllByProject(project.getId()));

                return project;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return null;
    }

    @Override
    public Project update(Project project) {

        PreparedStatement ps = null;

        String sql = "UPDATE project SET name = ?, owner_user_id = ? " +
                "WHERE id = ?;";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, project.getName());
            ps.setInt(2, project.getOwner().getId());
            ps.setInt(3, project.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 0) {
                return project;
            }
            throw new SQLException("project not found");

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closePreparedStatement(ps);
        }

        return null;
    }

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        PreparedStatement ps = null;

        String sql = "DELETE FROM project WHERE id = ? ";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("unable to remove project");
            }

        } catch (SQLException e) {
            throw new NotFoundException("Project Not Found");
        } finally {
            Database.closePreparedStatement(ps);
        }

    }

    @Override
    public List<User> findAllCollaboratorByProject(Integer idProject) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        List<User> collaborators = new ArrayList<>();

        try {
            String sql = "SELECT collaborator_project.project_id, collaborator_project.user_id, user.name, user.email, " +
                    "user.admin, user.created_at, user.updated_at FROM collaborator_project " +
                    "INNER JOIN user ON user.id = collaborator_project.user_id " +
                    "AND collaborator_project.project_id = ?;";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idProject);
            rs = ps.executeQuery();

            while (rs.next()) {
                User collaborator = new User();
                collaborator.setId(rs.getInt("user_id"));
                collaborator.setName(rs.getString("name"));
                collaborator.setEmail(rs.getString("email"));
                collaborator.setCreatedAt(rs.getDate("created_at").toLocalDate().atStartOfDay());
                collaborator.setUpdatedAt(rs.getDate("updated_at").toLocalDate().atStartOfDay());
                collaborators.add(collaborator);

            }

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return collaborators;

    }


    private Project getInstanceProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt("id"));
        project.setName(rs.getString("name"));
        return project;
    }

    private User getInstanceUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("owner_user_id"));
        user.setName(rs.getString("nameUser"));
        user.setEmail(rs.getString("email"));
        user.setCreatedAt(rs.getDate("userCreatedAt").toLocalDate().atStartOfDay());
        user.setUpdatedAt(rs.getDate("userUpdatedAt").toLocalDate().atStartOfDay());
        return user;
    }


}

