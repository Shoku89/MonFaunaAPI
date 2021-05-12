package com.monfauna.MonFaunaAPI.dto;

import com.monfauna.MonFaunaAPI.model.User;

import java.time.LocalDateTime;

public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private boolean admin;
    private String createdAt;
    private String updatedAt;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.admin = user.isAdmin();
        this.createdAt = user.getCreatedAt().toString();
        this.updatedAt = user.getUpdatedAt().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
