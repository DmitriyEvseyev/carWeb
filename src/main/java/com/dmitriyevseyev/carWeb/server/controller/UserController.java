package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.server.dao.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.UserDAO;
import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.user.AddUserExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserNotFoundExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserPasswordExeption;
import com.dmitriyevseyev.carWeb.shared.utils.PasswordHashGenerator;

import java.sql.SQLException;

public class UserController {
    private static UserController instance;
    private UserDAO userDAO;

    public static UserController getInstance() throws DAOFactoryActionException {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    private UserController() throws DAOFactoryActionException {
        ManagerDAO managerDAO = ManagerDAO.getInstance();
        this.userDAO = managerDAO.getDaoUser();
    }

    public boolean isUserExistServer(String userName, String userPassword) throws UserPasswordExeption {
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
            throw new UserPasswordExeption ("Password error! ");
        }
        return isCorrect;
    }

    public boolean isUserNameExistServer(String userName) throws UserNotFoundExeption {
        boolean isNameExist = false;
        try {
            isNameExist = userDAO.isUserExist(userName);
        } catch (SQLException e) {
            throw new UserNotFoundExeption("Error! User has not been found.");
        }
        return isNameExist;
    }

    public void addUser(User user) throws AddUserExeption {
        try {
            userDAO.createUser(user);
        } catch (SQLException e) {
            throw new AddUserExeption("Error! User has not been added. Try later.");
        }
    }
}

