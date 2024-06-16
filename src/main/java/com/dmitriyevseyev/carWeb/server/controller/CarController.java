package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.server.dao.interfaces.CarDAO;
import com.dmitriyevseyev.carWeb.server.dao.postgreSQL.PostgreSQLManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;
import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.CarIdAlreadyExistException;

import java.sql.SQLException;
import java.util.*;

public class CarController {
    private static CarController instance;
    private CarDAO CarDAO;

    public static CarController getInstance() throws DAOFactoryActionException {
        if (instance == null) {
            instance = new CarController();
        }
        return instance;
    }

    private CarController() throws DAOFactoryActionException {
        PostgreSQLManagerDAO postgreSQLManagerDAO = PostgreSQLManagerDAO.getInstance();
        this.CarDAO = postgreSQLManagerDAO.getDaoCar();
    }


    public List<Car> getCarList(Integer idDealer) throws GetAllCarExeption {
        return Collections.unmodifiableList(new ArrayList<>(CarDAO.getCarListDealer(idDealer)));
    }

    public List<Car> getCarsByDealersIds(List<Integer> ids) throws GetAllCarExeption {
        List<Car> carList = new ArrayList<>();
        for (Integer idDealer : ids) {
            carList.addAll(carList.size(), getCarList(idDealer));
        }
        return Collections.unmodifiableList(carList);
    }

    public void addCar(Car car) throws AddCarExeption {
        CarDAO.createCar(car);
    }

    public void removeCar(Integer id) throws NotFoundException, DeleteCarExeption {
        if (CarDAO.isCarExist(id)) {
            CarDAO.delete(id);
        }
    }

    public Car getCar(Integer id) throws NotFoundException {
        return CarDAO.getCar(id);
    }

    public List<Car> getCars(List<Integer> ids) throws NotFoundException {
        List<Car> carList = new ArrayList<>();
        for (Integer id : ids) {
            carList.add(getCar(id));
        }
        return Collections.unmodifiableList(carList);
    }

    public void updateCar(Car car) throws UpdateCarException {
        CarDAO.update(car);
    }

    public List<Car> getSortedByCriteria(Integer idDealer, String column, String criteria) throws GetAllCarExeption {
        return Collections.unmodifiableList(new ArrayList<>(CarDAO.getSortedByCriteria(idDealer, column, criteria)));
    }

    public List<Car> getFilteredByPattern(Integer idDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
        return Collections.unmodifiableList(new ArrayList<>(CarDAO.getFilteredByPattern(idDealer, column, pattern, criteria)));
    }

    public List<Car> getFilteredByDatePattern(Integer idDealer, String columnDate, Date startDatePattern, Date endDatePattern, String criteria) throws GetAllCarExeption {
        return Collections.unmodifiableList(new ArrayList<>(CarDAO.getFilteredByDatePattern(idDealer, columnDate, startDatePattern, endDatePattern, criteria)));
    }

    public List<Car> getFilteredByCrashPattern(Integer idDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
        return Collections.unmodifiableList(new ArrayList<>(CarDAO.getFilteredByCrashPattern(idDealer, column, pattern, criteria)));
    }

//    public void addCarWithId(Car car) throws CarIdAlreadyExistException, NotFoundException, AddCarExeption {
//        if (CarDAO.getCar(car.getId()) != null) {
//            throw new CarIdAlreadyExistException("Car with this id already exist: id = " + car.getId());
//        }
//        CarDAO.createCar(car);
//    }
}

