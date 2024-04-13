package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.server.dao.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.UserDAO;
import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.server.exceptions.user.AddUserExeption;
import com.dmitriyevseyev.carWeb.shared.utils.PasswordHashGenerator;

import java.sql.SQLException;

public class UserController {
    private static UserController instance;
    private UserDAO userDAO;

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    private UserController() {
        this.userDAO = ManagerDAO.getInstance().getDaoUser();
    }

    public boolean isUserExistServer(String userName, String userPassword) {
        PasswordHashGenerator passwordHashGenerator = PasswordHashGenerator.getInstance();
        boolean isCorrect = false;
        try {
            String password = passwordHashGenerator.getHashPassword(userPassword);
            String passwordServer = userDAO.getPassword(userName);
            if (passwordServer == null) {
                return isCorrect;
            } else {
                String passwordServerHash = passwordHashGenerator.getHashPassword(passwordServer);
                if (password.equals(passwordServerHash)) {
                    isCorrect = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("getPasswordError. " + e.getMessage());
        }
        return isCorrect;
    }

    public boolean isUserNameExistServer(String userName) {
        boolean isNameExist = false;
        try {
            isNameExist = userDAO.isUserExist(userName);
        } catch (SQLException e) {
            System.out.println("isUserNameExistServer. " + e.getMessage());
        }
        return isNameExist;
    }

    public void addUser(User user) throws AddUserExeption {
        try {
            userDAO.createUser(user);
        } catch (SQLException e) {
            throw new AddUserExeption(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }
}

