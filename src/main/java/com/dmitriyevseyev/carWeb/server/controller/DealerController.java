package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.dao.DealerDAO;
import com.dmitriyevseyev.carWeb.server.dao.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.*;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.DealerIdAlreadyExException;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.DealerNameAlreadyExistException;

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

    private DealerController() {
        this.dealerDAO = ManagerDAO.getInstance().getDealerDAO();
    }

    public List<CarDealership> getAllDealers() throws GetAllDealerExeption {
        try {
            return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getAll()));
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void addDealer(CarDealership dealer) throws AddDealerExeption {
        try {
            dealerDAO.createDealer(dealer);
        } catch (SQLException e) {
            throw new AddDealerExeption(String.format("AddDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void removeDealer(Integer id) throws NotFoundException, DeleteDealerExeption {
        try {
            if (!dealerDAO.isDealerExist(id)) {
                throw new NotFoundException("The dealer was not found!");
            } else {
                dealerDAO.delete(id);
            }
        } catch (SQLException e) {
            throw new DeleteDealerExeption(String.format("DeleteDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public CarDealership getDealer(Integer id) throws NotFoundException {
        try {
            return dealerDAO.getDealer(id);
        } catch (SQLException e) {
            throw new NotFoundException(String.format("The dealer was not found!  %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public List<CarDealership> getDealers(List<Integer> ids) throws NotFoundException {
        List<CarDealership> dealersList = new ArrayList<>();
        for (Integer id : ids) {
            dealersList.add(getDealer(id));
        }
        return Collections.unmodifiableList(dealersList);
    }

    public void updateDealer(Integer id, String name, String address) throws UpdateDealerException {
        try {
            dealerDAO.update(id, name, address);
        } catch (SQLException e) {
            throw new UpdateDealerException(String.format("EditDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public List<CarDealership> getSortedByCriteria(String column, String criteria) throws GetAllDealerExeption {
        try {
            return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getSortedByCriteria(column, criteria)));
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getSortedByCriteria: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public List<CarDealership> getFilteredByPattern(String column, String pattern, String criteria) throws GetAllDealerExeption {
        try {
            return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getFilteredByPattern(column, pattern, criteria)));
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getFilteredByPattern: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void addDealerWithId(CarDealership dealer) throws DealerNameAlreadyExistException, NotFoundException, AddDealerExeption, DealerIdAlreadyExException {
        try {
            if (dealerDAO.getDealerByName(dealer.getName()) != null) {
                throw new DealerNameAlreadyExistException("Dealer with this name, address already exist: name = " + dealer.getName());
            }
            if (dealerDAO.getDealer(dealer.getId()) != null) {
                throw new DealerIdAlreadyExException("Dealer with this id already exist: id = " + dealer.getId());
            }
        } catch (SQLException e) {
            throw new NotFoundException(String.format("The dealer was not found!  %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
        try {
            dealerDAO.createDealer(dealer);
        } catch (SQLException e) {
            throw new AddDealerExeption(String.format("AddDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public CarDealership getDealerByName(String name) throws NotFoundException {
        try {
            return dealerDAO.getDealerByName(name);
        } catch (SQLException e) {
            throw new NotFoundException(String.format("The dealer was not found!  %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }
}
