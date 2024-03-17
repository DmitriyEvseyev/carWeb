package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;
    private Connection connection;

    // singleton pattern
    public static UserDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new UserDAO(connection);
        }
        return instance;
    }

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // @Override
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD)  VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        }
    }

    //  @Override
    public User read(String userName) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        User user;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, userName);
            user = createCarByResultSet(stm.executeQuery());
        }
        return user;
    }

  /*  @Override
    public void update(Car car) throws SQLException {
        String sql = "UPDATE CAR SET NAME = ?, DATE = ?, COLOR = ?, ISAFTERCRASH = ?  WHERE ID = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setString(1, car.getName());
            stm.setDate(2, (new java.sql.Date(car.getDate().getTime())));
            stm.setString(3, car.getColor());
            stm.setBoolean(4, car.isAfterCrash());
            stm.setInt(5, car.getId());
            stm.executeUpdate();
        }
    }

   */

    // @Override
    public void delete(String userName) throws SQLException {
        String sql = "DELETE FROM USERS WHERE USERNAME = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setString(1, userName);
            stm.execute();
        }
    }

    //  @Override
    public List<User> getAll() throws SQLException {
        List<User> list;
        String sql = "SELECT * FROM USERS";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            list = createListByResultSet(statement.executeQuery());
        }
        return Collections.unmodifiableList(list);
    }
/*
    @Override
    public List<Car> getSortedByCriteria(Integer Id, String column, String criteria) throws SQLException {
        List<Car> list;
        String sql = "SELECT * FROM \"CAR\" WHERE \"Id\" = ? ORDER BY \"%s\" %s";
        sql = String.format(sql, column, criteria);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Id);
            list = createListByResultSet(statement.executeQuery());
        }
        return Collections.unmodifiableList(list);
    }
 */

    private User createCarByResultSet(ResultSet rs) throws SQLException {
        rs.next();
        User user = User.builder().id(rs.getInt("Id")).
                userName(rs.getString("UserName")).
                password(rs.getString("Password")).
                build();
        return user;
    }

    private List<User> createListByResultSet(ResultSet rs) throws SQLException {
        List<User> list = new LinkedList<>();
        while (rs.next()) {
            list.add(User.builder().id(rs.getInt("Id"))
                    .userName(rs.getString("UserName"))
                    .password(rs.getString("Password"))
                    .build());
        }
        return list;
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
            System.out.println("passw DAO - NO - " + password);
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
        System.out.println("passw DAO - " + password);
        return password;
    }

    public Integer getId(String userName) throws SQLException {
        Integer idUser = null;
        if (isUserExist(userName)) {
            String sqlGetPassword = "SELECT ID FROM USERS WHERE USERNAME = ?";
            try (PreparedStatement stm = connection.prepareStatement(sqlGetPassword)) {
                stm.setString(1, userName);
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    idUser = rs.getInt("ID");
                }
            }
        }
        return idUser;
    }
}





