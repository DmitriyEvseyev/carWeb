package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.server.dao.postgreSQL.PostgreSQLManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.postgreSQL.PostgreSQLUserDAO;
import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.user.AddUserExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserNotFoundExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserPasswordExeption;
import com.dmitriyevseyev.carWeb.shared.utils.PasswordHashGenerator;

import java.sql.SQLException;

public class UserController {
    private static UserController instance;
    private PostgreSQLUserDAO postgreSQLUserDAO;

    public static UserController getInstance() throws DAOFactoryActionException {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    private UserController() throws DAOFactoryActionException {
        PostgreSQLManagerDAO postgreSQLManagerDAO = PostgreSQLManagerDAO.getInstance();
        this.postgreSQLUserDAO = postgreSQLManagerDAO.getDaoUser();
    }

    public boolean isUserExistServer(String userName, String userPassword) throws UserPasswordExeption {
        System.out.println("isUserExistServer");

        PasswordHashGenerator passwordHashGenerator = PasswordHashGenerator.getInstance();
        boolean isCorrect = false;

        System.out.println("isCorrect = false;");

        try {

            System.out.println(123);

            String password = passwordHashGenerator.getHashPassword(userPassword);

            System.out.println(444444);

            String passwordServer = postgreSQLUserDAO.getPassword(userName);


            System.out.println("password - " + password);
            System.out.println("passwordServer - " + passwordServer );


            if (passwordServer == null) {
                return isCorrect;
            } else {
                String passwordServerHash = passwordHashGenerator.getHashPassword(passwordServer);


                System.out.println("passwordServerHash - " + passwordServerHash);


                if (password.equals(passwordServerHash)) {
                    isCorrect = true;

                    System.out.println("isCorrect - " + isCorrect);
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
            isNameExist = postgreSQLUserDAO.isUserExist(userName);
        } catch (SQLException e) {
            throw new UserNotFoundExeption("Error! User has not been found.");
        }
        return isNameExist;
    }

    public void addUser(User user) throws AddUserExeption {
        try {
            postgreSQLUserDAO.createUser(user);
        } catch (SQLException e) {
            throw new AddUserExeption("Error! User has not been added. Try later.");
        }
    }
}

