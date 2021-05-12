package com.monfauna.MonFaunaAPI.dto;

import com.monfauna.MonFaunaAPI.model.User;

import java.time.LocalDateTime;

public class NewUserDTO {

    private String name;
    private String email;
    private String password;
    private boolean admin;

    public NewUserDTO() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User toModel() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAdmin(admin);
        return user;
    }
}
