package com.monfauna.MonFaunaAPI.dto.input;

import com.monfauna.MonFaunaAPI.model.User;

public class UpdatedUserDTO {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toModel() {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return user;
    }
}
