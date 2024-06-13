package com.dmitriyevseyev.carWeb.server.dao.hibernate;

import com.dmitriyevseyev.carWeb.server.dao.interfaces.CarDAO;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.DealerDAO;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.UserDAO;

public class HibernateManagerDAO implements ManagerDAO {
    @Override
    public CarDAO getDaoCar() {
        return null;
    }

    @Override
    public UserDAO getDaoUser() {
        return null;
    }

    @Override
    public DealerDAO getDealerDAO() {
        return null;
    }
}
