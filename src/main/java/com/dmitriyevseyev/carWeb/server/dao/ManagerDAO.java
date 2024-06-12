package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.shared.utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ManagerDAO {
    public static Connection connection;
    public static ManagerDAO instance;
    private CarDAO carDAO;
    private UserDAO userDAO;
    private DealerDAO dealerDAO;

    public static ManagerDAO getInstance(String path) throws DAOFactoryActionException {
        if (instance == null) {
            instance = new ManagerDAO(path);
        }
        return instance;
    }

    public static ManagerDAO getInstance() throws DAOFactoryActionException {
        if (instance == null) {
            instance = new ManagerDAO();
        }
        return instance;
    }

    public ManagerDAO(String path) throws DAOFactoryActionException {
        connection = getConnect();
        executeSqlStartScript(path);
    }

    public ManagerDAO() throws DAOFactoryActionException {
        connection = getConnect();
    }

    public CarDAO getDaoCar() {
        return carDAO;
    }

    public UserDAO getDaoUser() {
        return userDAO;
    }

    public DealerDAO getDealerDAO() {
        return dealerDAO;
    }


    private void executeSqlStartScript(String path) throws DAOFactoryActionException {
        String delimiter = ";";
        Scanner scanner;
        try {
            scanner = new Scanner(new FileInputStream(path)).useDelimiter(delimiter);

            while (scanner.hasNext()) {
                String rawStatement = scanner.next() + delimiter;
                try (Statement currentStatement = connection.createStatement()) {

                    System.out.println("rawStatement DAO SQL - " + rawStatement);

                    currentStatement.executeUpdate(rawStatement);
                } catch (SQLException e) {
                    throw new DAOFactoryActionException(DAOConstants.STATEMENT_ERROR);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new DAOFactoryActionException(DAOConstants.START_SCRIPT_ERROR);
        }
    }

    public Connection getConnect() throws DAOFactoryActionException {
        Connection connection;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    Constants.JDBC,
                    Constants.USER,
                    Constants.PASSWORD);
            this.carDAO = CarDAO.getInstance(connection);
            this.userDAO = UserDAO.getInstance(connection);
            this.dealerDAO = DealerDAO.getInstance(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOFactoryActionException(DAOConstants.CONNECTION_ERROR);
        }
        return connection;
    }
}

