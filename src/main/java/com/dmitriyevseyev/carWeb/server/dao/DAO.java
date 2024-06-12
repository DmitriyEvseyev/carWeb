package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface DAO {
    void createCar(Car car) throws SQLException;

    List<Car> getCarListDealer(Integer idDealer) throws SQLException;

    Car getCar(Integer id) throws SQLException;

    void update(Car Car) throws SQLException;

    void delete(Integer id) throws SQLException;

    List<Car> createListByResultSet(ResultSet rs) throws SQLException;

    boolean isCarExist(Integer Id) throws SQLException;

    List<Car> getSortedByCriteria(Integer IdDealer, String column, String criteria) throws SQLException;

    List<Car> getFilteredByPattern(Integer IdDealer, String column, String pattern, String criteria) throws SQLException;

    List<Car> getFilteredByDatePattern(Integer IdDealer, String columnDate, Date startDatePattern, Date endDatePattern, String criteria) throws SQLException;

    List<Car> getFilteredByCrashPattern(Integer IdDealer, String column, String pattern, String criteria) throws SQLException;
}
