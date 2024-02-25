package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.model.Car;

import java.sql.SQLException;
import java.util.List;

public interface DAO {
    void createCar(Car car) throws SQLException;

    Car read(Integer id) throws SQLException;

    void update(Car Car) throws SQLException;

    void delete(Integer id) throws SQLException;

    List<Car> getAll() throws SQLException;

    List<Car> getSortedByCriteria(Integer Id, String column, String criteria) throws SQLException;

}
