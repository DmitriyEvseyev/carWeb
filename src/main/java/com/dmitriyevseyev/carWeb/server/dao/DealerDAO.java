package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DealerDAO {
    private static DealerDAO instance;
    private Connection connection;

    // singleton pattern
    public static DealerDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new DealerDAO(connection);
        }
        return instance;
    }

    public DealerDAO(Connection connection) {
        this.connection = connection;
    }

    // @Override
    public void createDealer(CarDealership dealer) throws SQLException {
        String sql = "INSERT INTO DEALERS (NAME, ADRESS)  VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dealer.getName());
            stmt.setString(2, dealer.getAdress());
            stmt.executeUpdate();
        }
    }

    //@Override
    public CarDealership read(Integer id) throws SQLException {
        String sql = "SELECT * FROM DEALERS WHERE id = ?";
        CarDealership dealer;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            dealer = createDealerByResultSet(stm.executeQuery());
        }
        return dealer;
    }

   // @Override
    public void update(Integer id, String name, String adress) throws SQLException {
        String sql = "UPDATE DEALERS SET NAME = ?, ADRESS = ?  WHERE ID = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setString(1, name);
            stm.setString(2, adress);
            stm.setInt(3, id);
            stm.executeUpdate();
        }
    }

 //   @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM DEALERS WHERE Id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setInt(1, id);
            stm.execute();
        }
    }

 //   @Override
    public List<CarDealership> getAll() throws SQLException {
        List<CarDealership> list;
        String sql = "SELECT * FROM DEALERS";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            list = createListByResultSet(statement.executeQuery());
        }
        return Collections.unmodifiableList(list);
    }

  /*  @Override
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


    private CarDealership createDealerByResultSet(ResultSet rs) throws SQLException {
        rs.next();
        CarDealership dealer = CarDealership.builder()
                .id(rs.getInt("Id"))
                .name(rs.getString("Name"))
                .adress(rs.getString("Adress"))
                .build();
        return dealer;
    }

    private List<CarDealership> createListByResultSet(ResultSet rs) throws SQLException {
        List<CarDealership> list = new LinkedList<>();
        while (rs.next()) {
            list.add(CarDealership.builder().id(rs.getInt("Id"))
                    .name(rs.getString("Name"))
                    .adress(rs.getString("Adress"))
                    .build());
        }
        return list;
    }

    public boolean isDealerExist(Integer Id) throws SQLException {
        boolean dealerExist;
        String sqlExistCar = "SELECT * FROM DEALERS WHERE Id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sqlExistCar)) {
            stm.setInt(1, Id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                dealerExist = true;
            } else {
                dealerExist = false;
            }
        }
        return dealerExist;
    }
}





