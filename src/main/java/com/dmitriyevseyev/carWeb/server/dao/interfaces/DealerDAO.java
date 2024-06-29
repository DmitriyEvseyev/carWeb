package com.dmitriyevseyev.carWeb.server.dao.interfaces;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.DeleteDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetAllDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.UpdateDealerException;

import java.sql.SQLException;
import java.util.List;

public interface DealerDAO {
    void createDealer(CarDealership dealer) throws AddDealerExeption;

    CarDealership getDealer(Integer id) throws NotFoundException;

    CarDealership getDealerByName(String name) throws NotFoundException;

    void update(Integer id, String name, String address) throws UpdateDealerException;

    void delete(Integer id) throws DeleteDealerExeption;

    List<CarDealership> getAll() throws GetAllDealerExeption;

    List<CarDealership> getSortedByCriteria(String column, String criteria) throws GetAllDealerExeption;

    List<CarDealership> getFilteredByPattern(String column, String pattern, String criteria) throws GetAllDealerExeption;
}
