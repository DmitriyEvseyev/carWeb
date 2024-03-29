package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.server.dao.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.UserDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.AddCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.DeleteCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.server.exceptions.user.AddUserExeption;
import com.dmitriyevseyev.carWeb.shared.utils.PasswordHashGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            System.out.println("passwordServer - " + passwordServer);
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
        System.out.println("isUserExistServer - " + isCorrect);
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

        public Integer getIdUser(User user) {
        Integer idUser = 0;
        try {
            idUser = userDAO.getId(user.getUserName());
        } catch (SQLException e) {
            System.out.println("getIdUserError. " + e.getMessage());
        }
        return idUser;
    }

    public List<User> getAllUsers() throws GetAllCarExeption {
        // dao
        try {
            return Collections.unmodifiableList(new ArrayList<>(userDAO.getAll()));
        } catch (SQLException e) {
            throw new GetAllCarExeption(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void addUser(User user) throws  AddUserExeption {
        // dao
        try {
            userDAO.createUser(user);
        } catch (SQLException e) {
            throw new AddUserExeption(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void removeCar(String userName) throws NotFoundException, DeleteCarExeption {
        //dao
        try {
            if (!userDAO.isUserExist(userName)) {
                throw new NotFoundException();
            }
            userDAO.delete(userName);
        } catch (SQLException e) {
            throw new DeleteCarExeption(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

   /* public void updateCar(Car car) throws UpdateCarException {
        // dao
        Car updateCar;
        try {
            updateCar = Car.builder()
                    .id(car.getId())
                    .name(car.getName())
                    .date(car.getDate())
                    .color(car.getColor())
                    .isAfterCrash(car.isAfterCrash())
                    .build();
            daoCar.update(updateCar);
        } catch (SQLException e) {
            throw new UpdateCarException(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    */
}

