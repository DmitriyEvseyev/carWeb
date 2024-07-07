package com.dmitriyevseyev.carWeb.server.dao.postgreSQL;

import com.dmitriyevseyev.carWeb.server.dao.DAOConstants;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;

import java.io.*;
import java.net.URL;
import java.sql.*;

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

    @Override
    public void executeSqlStartScript(String path) throws DAOFactoryActionException {
        StringBuilder rawStatement = new StringBuilder();
        File file = new File(getClass().getClassLoader().getResource(path).getFile());
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String ls = System.getProperty("line.separator");
            while ((line = br.readLine()) != null) {
                rawStatement.append(line);
                rawStatement.append(ls);
            }
            try (Statement currentStatement = connection.createStatement()) {

                System.out.println("rawStatement DAO SQL - " + rawStatement);

                currentStatement.executeUpdate(String.valueOf(rawStatement));
            } catch (SQLException e) {
                throw new DAOFactoryActionException(DAOConstants.STATEMENT_ERROR);
            }
        } catch (IOException e) {
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

