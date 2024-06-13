package com.dmitriyevseyev.carWeb.server.dao.interfaces;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface CarDAO {
    void createCar(Car car) throws AddCarExeption;

    List<Car> getCarListDealer(Integer idDealer) throws  GetAllCarExeption;

    Car getCar(Integer id) throws  NotFoundException;

    void update(Car Car) throws  UpdateCarException;

    void delete(Integer id) throws  DeleteCarExeption;

    List<Car> createListByResultSet(ResultSet rs) throws SQLException, GetAllCarExeption;

    boolean isCarExist(Integer Id) throws  NotFoundException;

    List<Car> getSortedByCriteria(Integer IdDealer, String column, String criteria) throws GetAllCarExeption;

    List<Car> getFilteredByPattern(Integer IdDealer, String column, String pattern, String criteria) throws  GetAllCarExeption;

    List<Car> getFilteredByDatePattern(Integer IdDealer, String columnDate, Date startDatePattern, Date endDatePattern, String criteria) throws  GetAllCarExeption;

    List<Car> getFilteredByCrashPattern(Integer IdDealer, String column, String pattern, String criteria) throws  GetAllCarExeption;
}
