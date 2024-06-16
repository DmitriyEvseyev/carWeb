package com.dmitriyevseyev.carWeb.server.dao.postgreSQL;

import com.dmitriyevseyev.carWeb.server.dao.DAOConstants;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class PostgreSQLManagerDAO implements ManagerDAO {
    public static Connection connection;
    public static PostgreSQLManagerDAO instance;

    public static PostgreSQLManagerDAO getInstance(String path) throws DAOFactoryActionException {
        if (instance == null) {
            instance = new PostgreSQLManagerDAO(path);
        }
        return instance;
    }

    public static PostgreSQLManagerDAO getInstance() throws DAOFactoryActionException {
        if (instance == null) {
            instance = new PostgreSQLManagerDAO();
        }
        return instance;
    }

    public PostgreSQLManagerDAO(String path) throws DAOFactoryActionException {
        connection = getConnect();
        executeSqlStartScript(path);
    }

    public PostgreSQLManagerDAO() throws DAOFactoryActionException {
        connection = getConnect();
    }

    public PostgreSQLCarDAO getDaoCar() {
        return PostgreSQLCarDAO.getInstance(connection);
    }

    public PostgreSQLUserDAO getDaoUser() {
        return PostgreSQLUserDAO.getInstance(connection);
    }

    public PostgreSQLDealerDAO getDealerDAO() {
        return PostgreSQLDealerDAO.getInstance(connection);
    }


    private void executeSqlStartScript(String path) throws DAOFactoryActionException {
        String delimiter = ";";
        Scanner scanner;
        try {
            scanner = new Scanner(new FileInputStream(path)).useDelimiter(delimiter);

            while (scanner.hasNext()) {
                String rawStatement = scanner.next() + delimiter;
                try (Statement currentStatement = connection.createStatement()) {
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
                    DAOConstants.JDBC,
                    DAOConstants.USER,
                    DAOConstants.PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOFactoryActionException(DAOConstants.CONNECTION_ERROR);
        }
        return connection;
    }
}

