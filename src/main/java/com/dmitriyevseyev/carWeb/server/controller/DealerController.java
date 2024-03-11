package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.dao.CarDAO;
import com.dmitriyevseyev.carWeb.server.dao.DealerDAO;
import com.dmitriyevseyev.carWeb.server.dao.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DealerController {
    private static DealerController instance;
    private DealerDAO dealerDAO;

    public static DealerController getInstance() {
        if (instance == null) {
            instance = new DealerController();
        }
        return instance;
    }

    public DealerDAO getDaoDealer() {
        return dealerDAO;
    }

    private DealerController() {
        this.dealerDAO = ManagerDAO.getInstance().getDealerDAO();
    }

    public List<CarDealership> getAllDealers() throws GetAllDealerExeption {
        // dao
        try {
            return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getAll()));
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void addDealer(CarDealership dealer) throws AddDealerExeption {
        // dao
        try {
            dealerDAO.createDealer(dealer);
        } catch (SQLException e) {
            throw new AddDealerExeption(String.format("AddDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void removeDealer(Integer id) throws NotFoundException, DeleteDealerExeption {
        //dao
        try {
            if (!dealerDAO.isDealerExist(id)) {
                throw new NotFoundException();
            }
            dealerDAO.delete(id);
        } catch (SQLException e) {
            throw new DeleteDealerExeption(String.format("DeleteDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public CarDealership getDealer (Integer id) throws GetDealerException, NotFoundException {
        CarDealership dealer = null;
        try {
            if (!dealerDAO.isDealerExist(id)) {
                throw new NotFoundException();
            }
            dealer = dealerDAO.read(id);
        } catch (SQLException e) {
            throw new GetDealerException(String.format("GetDealerException: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
        return dealer;
    }

    public void updateCar(Integer id, String name, String adress) throws UpdateDealerException {
        // dao
        try {
            dealerDAO.update(id, name, adress);
        } catch (SQLException e) {
            throw new UpdateDealerException(String.format("EditDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }
}
