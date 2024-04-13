package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.model.User;

import java.sql.*;

public class UserDAO {
    private static UserDAO instance;
    private Connection connection;

       public static UserDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new UserDAO(connection);
        }
        return instance;
    }

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

      public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD)  VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        }
    }

    public boolean isUserExist(String userName) throws SQLException {
        boolean userExist;
        String sqlExistUser = "SELECT * FROM USERS WHERE USERNAME = ?";
        try (PreparedStatement stm = connection.prepareStatement(sqlExistUser)) {
            stm.setString(1, userName);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                userExist = true;
            } else {
                userExist = false;
            }
        }
        return userExist;
    }

    public String getPassword(String userName) throws SQLException {
        String password = null;
        if (!isUserExist(userName)) {
                        return password;
        } else {
            String sqlGetPassword = "SELECT PASSWORD FROM USERS WHERE USERNAME = ?";
            try (PreparedStatement stm = connection.prepareStatement(sqlGetPassword)) {
                stm.setString(1, userName);
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    password = rs.getString("PASSWORD");
                }
            }
        }
               return password;
    }
}





