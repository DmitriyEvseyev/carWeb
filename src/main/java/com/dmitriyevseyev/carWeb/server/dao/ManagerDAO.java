package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.shared.utils.Constants;

import java.sql.*;

public class ManagerDAO {
    public static Connection connection;
    public static ManagerDAO instance;
    private CarDAO carDAO;
    private UserDAO userDAO;

    public static ManagerDAO getInstance() {
        if (instance == null) {
            instance = new ManagerDAO();
        }
        return instance;
    }

    public CarDAO getDaoCar() {
        return carDAO;
    }

    public UserDAO getDaoUser() {
        return userDAO;
    }

    public ManagerDAO() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    Constants.JDBC,
                    Constants.USER,
                    Constants.PASSWORD);
            this.carDAO = CarDAO.getInstance(connection);
            this.userDAO = UserDAO.getInstance(connection);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }

        try (Statement stmt = connection.createStatement()) {
            String sqlCar = "CREATE TABLE IF NOT EXISTS CAR" +
                    "(ID INT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY, " +
                    " NAME           VARCHAR     NOT NULL, " +
                    " DATE           DATE        NOT NULL, " +
                    " COLOR          VARCHAR     NOT NULL, " +
                    " ISAFTERCRASH   BOOLEAN, " +
                    " IDDEALER        INT, " +
                    " FOREIGN KEY (IDDEALER) REFERENCES DEALERS(ID) ON UPDATE CASCADE ON DELETE CASCADE)";
            stmt.executeUpdate(sqlCar);

            String sqlUser = "CREATE TABLE IF NOT EXISTS USERS" +
                    "(ID INT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY, " +
                    " USERNAME       VARCHAR     NOT NULL, " +
                    " PASSWORD       VARCHAR     NOT NULL)";
            stmt.executeUpdate(sqlUser);

            String sqlDealer = "CREATE TABLE IF NOT EXISTS DEALERS" +
                    "(ID INT PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY, " +
                    " NAME       VARCHAR     NOT NULL, " +
                    " ADRESS     VARCHAR     NOT NULL)";
            stmt.executeUpdate(sqlDealer);

            System.out.println("Opened database successfully");

        } catch (SQLException e) {
            System.out.println("createTableError. " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}

