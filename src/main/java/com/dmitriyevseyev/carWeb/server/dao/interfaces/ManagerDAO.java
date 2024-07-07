package com.dmitriyevseyev.carWeb.server.dao.interfaces;

import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;

public interface ManagerDAO {
    public CarDAO getDaoCar();

    public UserDAO getDaoUser();

    public DealerDAO getDealerDAO();

    void executeSqlStartScript(String path) throws DAOFactoryActionException;

}
