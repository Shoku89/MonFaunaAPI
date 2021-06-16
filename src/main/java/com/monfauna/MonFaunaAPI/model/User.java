package com.monfauna.MonFaunaAPI.model;


import com.monfauna.MonFaunaAPI.exception.InvalidResourceException;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//classe User não pode ser instanciada pq é uma classe abstrata e estas não podem ser concretizadas,
//apenas definem como suas subclasses funcionam
public class User {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean admin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(Integer id, String name, String email, String password, boolean admin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void validate() {

        if (name == null || name.isBlank()) {
            throw new InvalidResourceException("Name can't be null or empty");
        }
        if (email == null || email.isBlank()) {
            throw new InvalidResourceException("E-mail can't be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new InvalidResourceException("Password can't be null or empty");
        }
        if (!isValidEmail(email)) {
            throw new InvalidResourceException("E-mail not valid");
        }

    }

    public boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}

