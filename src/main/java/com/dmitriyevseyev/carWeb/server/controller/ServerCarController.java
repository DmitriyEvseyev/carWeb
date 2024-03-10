package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.server.dao.CarDAO;
import com.dmitriyevseyev.carWeb.server.dao.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;
import com.dmitriyevseyev.carWeb.model.Car;

import java.sql.SQLException;
import java.util.*;

public class ServerCarController {
    private static ServerCarController instance;
    private CarDAO carDAO;

    public static ServerCarController getInstance() {
        if (instance == null) {
            instance = new ServerCarController();
        }
        return instance;
    }

    public CarDAO getDaoCar() {
        return carDAO;
    }

    private ServerCarController() {
        this.carDAO = ManagerDAO.getInstance().getDaoCar();
    }

    public List<Car> getAllCars() throws GetAllCarExeption {
        // dao
        try {
            return Collections.unmodifiableList(new ArrayList<>(carDAO.getAll()));
        } catch (SQLException e) {
            throw new GetAllCarExeption(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void addCar(Car car) throws AddCarExeption {
        // dao
        try {
            carDAO.createCar(car);
        } catch (SQLException e) {
            throw new AddCarExeption(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void removeCar(Integer id) throws NotFoundException, DeleteCarExeption {
        //dao
        try {
            if (!carDAO.isCarExist(id)) {
                throw new NotFoundException();
            }
            carDAO.delete(id);
        } catch (SQLException e) {
            throw new DeleteCarExeption(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void updateCar(Car car) throws UpdateCarException {
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
            carDAO.update(updateCar);
        } catch (SQLException e) {
            throw new UpdateCarException(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }
}
