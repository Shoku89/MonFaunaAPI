package com.monfauna.MonFaunaAPI.service;

import com.monfauna.MonFaunaAPI.dao.UserDao;
import com.monfauna.MonFaunaAPI.dao.impl.DaoFactory;
import com.monfauna.MonFaunaAPI.exception.BusinessRulesException;
import com.monfauna.MonFaunaAPI.exception.InvalidResourceException;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        this.userDao = DaoFactory.getUserDao();
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(Integer id) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        } else {
            return user;
        }
    }

    public User save(User user) {
        user.validate(); //throws exception if user is not valid
        User userRegistered = userDao.findByEmail(user.getEmail());
        if (userRegistered != null) {
            throw new BusinessRulesException("E-mail already exists");
        } else {
            return userDao.save(user);
        }
    }

    public User update(Integer id, User userUpdated) throws NotFoundException {
        User user = this.findById(id);

        if (userUpdated.getPassword() == null || userUpdated.getPassword().isBlank()) {
            throw new InvalidResourceException("Password cannot be empty");
        } else {
            user.setPassword(userUpdated.getPassword());
        }
        if (userUpdated.getName() == null || userUpdated.getName().isBlank()) {
            throw new InvalidResourceException("Name cannot be empty");
        } else {
            user.setName(userUpdated.getName());
        }


        user.setUpdatedAt(LocalDateTime.now());

        return userDao.update(user);

    }

    public void deleteById(Integer id) throws NotFoundException {
        userDao.deleteById(id);
    }

}
