package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.dao.postgreSQL.PostgreSQLDealerDAO;
import com.dmitriyevseyev.carWeb.server.dao.postgreSQL.PostgreSQLManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DealerController {
    private static DealerController instance;
    private PostgreSQLDealerDAO postgreSQLDealerDAO;

    public static DealerController getInstance() throws DAOFactoryActionException {
        if (instance == null) {
            instance = new DealerController();
        }
        return instance;
    }

    private DealerController() throws DAOFactoryActionException {
        PostgreSQLManagerDAO postgreSQLManagerDAO = PostgreSQLManagerDAO.getInstance();
        this.postgreSQLDealerDAO = postgreSQLManagerDAO.getDealerDAO();
    }

    public List<CarDealership> getAllDealers() throws GetAllDealerExeption {
        try {
            return Collections.unmodifiableList(new ArrayList<>(postgreSQLDealerDAO.getAll()));
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void addDealer(CarDealership dealer) throws AddDealerExeption {
        try {
            postgreSQLDealerDAO.createDealer(dealer);
        } catch (SQLException e) {
            throw new AddDealerExeption(String.format("AddDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public void removeDealer(Integer id) throws NotFoundException, DeleteDealerExeption {
        try {
            if (!postgreSQLDealerDAO.isDealerExist(id)) {
                throw new NotFoundException("The dealer was not found!");
            } else {
                postgreSQLDealerDAO.delete(id);
            }
        } catch (SQLException e) {
            throw new DeleteDealerExeption(String.format("DeleteDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public CarDealership getDealer(Integer id) throws NotFoundException {
        try {
            return postgreSQLDealerDAO.getDealer(id);
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
            postgreSQLDealerDAO.update(id, name, address);
        } catch (SQLException e) {
            throw new UpdateDealerException(String.format("EditDealerExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public List<CarDealership> getSortedByCriteria(String column, String criteria) throws GetAllDealerExeption {
        try {
            return Collections.unmodifiableList(new ArrayList<>(postgreSQLDealerDAO.getSortedByCriteria(column, criteria)));
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getSortedByCriteria: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public List<CarDealership> getFilteredByPattern(String column, String pattern, String criteria) throws GetAllDealerExeption {
        try {
            return Collections.unmodifiableList(new ArrayList<>(postgreSQLDealerDAO.getFilteredByPattern(column, pattern, criteria)));
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getFilteredByPattern: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public CarDealership getDealerByName(String name) throws NotFoundException {
        try {
            return postgreSQLDealerDAO.getDealerByName(name);
        } catch (SQLException e) {
            throw new NotFoundException(String.format("The dealer was not found!  %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }
}
