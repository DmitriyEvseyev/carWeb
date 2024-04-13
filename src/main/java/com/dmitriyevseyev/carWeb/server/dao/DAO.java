package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.model.Car;

import java.sql.SQLException;

public interface DAO {
    void createCar(Car car) throws SQLException;

    void update(Car Car) throws SQLException;

    void delete(Integer id) throws SQLException;

}
