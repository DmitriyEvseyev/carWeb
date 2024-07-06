package com.dmitriyevseyev.carWeb.server.controller;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.dao.hibernate.HibernateManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.DealerDAO;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.postgreSQL.PostgreSQLDealerDAO;
import com.dmitriyevseyev.carWeb.server.dao.postgreSQL.PostgreSQLManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DealerController {
    private static DealerController instance;
    private DealerDAO  dealerDAO;

    public static DealerController getInstance() throws DAOFactoryActionException {
        if (instance == null) {
            instance = new DealerController();
        }
        return instance;
    }

    private DealerController() {
        ManagerDAO managerDAO = HibernateManagerDAO.getInstance();
        this.dealerDAO = managerDAO.getDealerDAO();
    }

    public void addDealer(CarDealership dealer) throws AddDealerExeption {
        dealerDAO.createDealer(dealer);
    }

    public CarDealership getDealer(Integer id) throws NotFoundException {
        return dealerDAO.getDealer(id);
    }

    public CarDealership getDealerByName(String name) throws NotFoundException {
        return dealerDAO.getDealerByName(name);
    }

    public List<CarDealership> getAllDealers() throws GetAllDealerExeption {
        return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getAll()));
    }

    public void updateDealer(CarDealership dealer) throws UpdateDealerException {
        dealerDAO.update(dealer);
    }

    public void removeDealer(Integer id) throws DeleteDealerExeption {
        dealerDAO.delete(id);
    }

    public List<CarDealership> getDealers(List<Integer> ids) throws NotFoundException {
        List<CarDealership> dealersList = new ArrayList<>();
        for (Integer id : ids) {
            dealersList.add(getDealer(id));
        }
        return Collections.unmodifiableList(dealersList);
    }

    public List<CarDealership> getSortedByCriteria(String column, String criteria) throws GetAllDealerExeption {
        return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getSortedByCriteria(column, criteria)));
    }

    public List<CarDealership> getFilteredByPattern(String column, String pattern, String criteria) throws GetAllDealerExeption {
        return Collections.unmodifiableList(new ArrayList<>(dealerDAO.getFilteredByPattern(column, pattern, criteria)));
    }
}
