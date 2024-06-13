package com.dmitriyevseyev.carWeb.server.dao.hibernate;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.CarDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class HibernateCarDAO implements CarDAO {
    @Override
    public void createCar(Car car) throws AddCarExeption {

    }

    @Override
    public List<Car> getCarListDealer(Integer idDealer) throws GetAllCarExeption {
        return null;
    }

    @Override
    public Car getCar(Integer id) throws NotFoundException {
        return null;
    }

    @Override
    public void update(Car Car) throws UpdateCarException {

    }

    @Override
    public void delete(Integer id) throws DeleteCarExeption {

    }

    @Override
    public List<Car> createListByResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public boolean isCarExist(Integer Id) throws NotFoundException {
        return false;
    }

    @Override
    public List<Car> getSortedByCriteria(Integer IdDealer, String column, String criteria) throws GetAllCarExeption {
        return null;
    }

    @Override
    public List<Car> getFilteredByPattern(Integer IdDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
        return null;
    }

    @Override
    public List<Car> getFilteredByDatePattern(Integer IdDealer, String columnDate, Date startDatePattern, Date endDatePattern, String criteria) throws GetAllCarExeption {
        return null;
    }

    @Override
    public List<Car> getFilteredByCrashPattern(Integer IdDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
        return null;
    }
}
