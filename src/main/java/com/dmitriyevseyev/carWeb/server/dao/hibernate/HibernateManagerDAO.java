package com.dmitriyevseyev.carWeb.server.dao.hibernate;

import com.dmitriyevseyev.carWeb.server.dao.DAOConstants;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.CarDAO;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.DealerDAO;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.UserDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import org.hibernate.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class HibernateManagerDAO implements ManagerDAO {
    private static HibernateManagerDAO instance;
    private SessionFactory sessionFactory;

    private HibernateManagerDAO() {
        sessionFactory = HibernateSessionFactoryUtil.getInstance().getSessionFactory();
    }

    public static HibernateManagerDAO getInstance() {
        if (instance == null)
            instance = new HibernateManagerDAO();
        return instance;
    }

    private HibernateManagerDAO(String path) throws DAOFactoryActionException {
        executeSqlStartScript(path);
        sessionFactory = HibernateSessionFactoryUtil.getInstance().getSessionFactory();
    }

    public static HibernateManagerDAO getInstance(String path) throws DAOFactoryActionException {
        if (instance == null)
            instance = new HibernateManagerDAO(path);
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public CarDAO getCarDAO() {
        return HibernateCarDAO.getInstance(getSessionFactory());
    }

    @Override
    public DealerDAO getDealerDAO() {
        return HibernateDealerDAO.getInstance(getSessionFactory());
    }

    @Override
    public UserDAO getDaoUser() {
        return HibernateUserDAO.getInstance(getSessionFactory());
    }

    @Override
    public void executeSqlStartScript(String path) throws DAOFactoryActionException {
        Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        StringBuilder rawStatement = new StringBuilder();

        //       File file = new File(getClass().getClassLoader().getResource("database.properties").getFile());
        File file;
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("script.sql is not found!");
        } else {
            file = new File(resource.getFile());
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String ls = System.getProperty("line.separator");
            while ((line = br.readLine()) != null) {
                rawStatement.append(line);
                rawStatement.append(ls);
            }


            System.out.println("rawStatement - " + rawStatement.toString());


            SQLQuery sqlQuery = session.createSQLQuery(rawStatement.toString());
            sqlQuery.executeUpdate();
            tx.commit();
            session.close();
        } catch (IOException | HibernateException e) {
            throw new DAOFactoryActionException(DAOConstants.START_SCRIPT_ERROR + e.getMessage());
        }
    }
}
