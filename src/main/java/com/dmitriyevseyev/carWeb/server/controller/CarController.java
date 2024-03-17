package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.server.dao.CarDAO;
import com.dmitriyevseyev.carWeb.server.dao.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;
import com.dmitriyevseyev.carWeb.model.Car;

import java.sql.SQLException;
import java.util.*;

public class CarController {
    private static CarController instance;
    private CarDAO carDAO;

    public static CarController getInstance() {
        if (instance == null) {
            instance = new CarController();
        }
        return instance;
    }

    public CarDAO getDaoCar() {
        return carDAO;
    }

    private CarController() {
        this.carDAO = ManagerDAO.getInstance().getDaoCar();
    }

    public List<Car> getCarList(Integer idDealer) throws GetAllCarExeption {
        // dao
        try {
            return Collections.unmodifiableList(new ArrayList<>(carDAO.getCarListDealer(idDealer)));
        } catch (SQLException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption. Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
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

    public Car getCar(Integer id) throws UpdateCarException {
        try {
            return carDAO.getCar(id);
        } catch (SQLException e) {
            throw new UpdateCarException(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void updateCar(Car car) throws UpdateCarException {
        try {
            carDAO.update(car);
        } catch (SQLException e) {
            throw new UpdateCarException(String.format("Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }
}

