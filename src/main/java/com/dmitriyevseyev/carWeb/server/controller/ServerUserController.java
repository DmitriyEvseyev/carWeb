package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.server.dao.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.UserDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.AddCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.DeleteCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.shared.utils.PasswordHashGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerUserController {
    private static ServerUserController instance;
    private UserDAO userDAO;

    public static ServerUserController getInstance() {
        if (instance == null) {
            instance = new ServerUserController();
        }
        return instance;
    }

    private ServerUserController() {
        this.userDAO = ManagerDAO.getInstance().getDaoUser();
    }

    public boolean isUserExistServer(User user) {
        PasswordHashGenerator passwordHashGenerator =  PasswordHashGenerator.getInstance();
        boolean isCorrect = false;
        try {
            String password = passwordHashGenerator.getHashPassword(user.getPassword());
            String passwordServer = passwordHashGenerator.getHashPassword(userDAO.getPassword(user.getUserName()));
            if (password.equals(passwordServer)) {
                isCorrect = true;
            }
        } catch (SQLException e) {
            System.out.println("getPasswordError. " + e.getMessage());
        }
        return isCorrect;
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

    public void addUser(User user) throws AddCarExeption {
        // dao
        try {
            userDAO.createUser(user);
        } catch (SQLException e) {
            throw new AddCarExeption(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
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

