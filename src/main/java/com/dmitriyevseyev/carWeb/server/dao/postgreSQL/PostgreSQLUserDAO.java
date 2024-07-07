package com.dmitriyevseyev.carWeb.server.dao.postgreSQL;

import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.UserDAO;

import java.sql.*;

public class PostgreSQLUserDAO implements UserDAO {
    private static PostgreSQLUserDAO instance;
    private Connection connection;

    public static PostgreSQLUserDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new PostgreSQLUserDAO(connection);
        }
        return instance;
    }

    public PostgreSQLUserDAO(Connection connection) {
        this.connection = connection;
    }

    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO USERS (user_name, user_password)  VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        }
    }

    public boolean isUserExist(String userName) throws SQLException {
        boolean userExist;
        String sqlExistUser = "SELECT * FROM USERS WHERE user_name = ?";
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
            String sqlGetPassword = "SELECT user_password FROM USERS WHERE user_name = ?";
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





