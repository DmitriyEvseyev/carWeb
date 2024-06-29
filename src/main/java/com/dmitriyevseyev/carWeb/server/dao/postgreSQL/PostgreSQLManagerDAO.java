package com.dmitriyevseyev.carWeb.server.dao.postgreSQL;

import com.dmitriyevseyev.carWeb.server.dao.DAOConstants;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.shared.utils.Constants;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.*;
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

    public PostgreSQLCarDAO getCarDAO() {
        return PostgreSQLCarDAO.getInstance(connection);
    }

    public PostgreSQLUserDAO getDaoUser() {
        return PostgreSQLUserDAO.getInstance(connection);
    }

    public PostgreSQLDealerDAO getDealerDAO() {
        return PostgreSQLDealerDAO.getInstance(connection);
    }


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
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    Constants.JDBC,
                    Constants.USER,
                    Constants.PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOFactoryActionException(DAOConstants.CONNECTION_ERROR);
        }


//        final String JDBC_DB_NAME = "java:comp/env/jdbc/cars";
//
//        DataSource dataSource;
//        Context ctx;
//
//        Context initialContext = null;
//        try {
//            initialContext = new InitialContext();
//            Context envContext = (Context) initialContext.lookup("java:/comp/env");
//            DataSource ds = (DataSource) envContext.lookup("jdbc/cars");
//
//        } catch (NamingException e) {
//            throw new RuntimeException(e);
//        }
//
//        Connection connection = null;
//        try {
//            System.out.println("CONNDKF - " + ctx.getNameInNamespace());
//            System.out.println("FFFFFFFFFFF");
//            System.out.println("DDDDDDD - " + dataSource.getLoginTimeout());
//
//
//            connection = dataSource.getConnection();
//
//
//            System.out.println("CCCCCCCC - " + connection);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (NamingException e) {
//            throw new RuntimeException(e);
//        }
        return connection;
    }
}

