package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.model.Car;

import java.sql.*;
import java.util.*;

public class CarDAO implements DAO {
    private static CarDAO instance;
    private Connection connection;

    // singleton pattern
    public static CarDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new CarDAO(connection);
        }
        return instance;
    }

    public CarDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createCar(Car car) throws SQLException {
        String sql = "INSERT INTO CAR (NAME, DATE, COLOR, ISAFTERCRASH)  VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, car.getName());
            stmt.setDate(2, (new java.sql.Date(car.getDate().getTime())));
            stmt.setString(3, car.getColor());
            stmt.setBoolean(4, car.isAfterCrash());
            stmt.executeUpdate();
        }
    }

     public List<Car> getCarListDealer(Integer idDealer) throws SQLException {
        String sql = "SELECT * FROM CAR WHERE IDDEALER = ?";
        List<Car> list = null;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, idDealer);
            list = createListByResultSet(stm.executeQuery());
        }
        return Collections.unmodifiableList(list);
    }

    @Override
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

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM CAR WHERE Id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setInt(1, id);
            stm.execute();
        }
    }

    private List<Car> createListByResultSet(ResultSet rs) throws SQLException {
        List<Car> list = new LinkedList<>();
        while (rs.next()) {
            list.add(Car.builder()
                    .id(rs.getInt("Id"))
                    .idDealer(rs.getInt("IdDealer"))
                    .name(rs.getString("Name"))
                    .color(rs.getString("Color"))
                    .date(rs.getDate("Date"))
                    .isAfterCrash(rs.getBoolean("isAfterCrash"))
                    .build());
        }
        return list;
    }

    public boolean isCarExist(Integer Id) throws SQLException {
        boolean carExist;
        String sqlExistCar = "SELECT * FROM CAR WHERE Id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sqlExistCar)) {
            stm.setInt(1, Id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                carExist = true;
            } else {
                carExist = false;
            }
        }
        return carExist;
    }

    /*

    @Override
    public List<Car> getAll() throws SQLException {
        List<Car> list;
        String sql = "SELECT * FROM CAR";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            list = createListByResultSet(statement.executeQuery());
        }
        return Collections.unmodifiableList(list);
    }@Override

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


    private Car createCarByResultSet(ResultSet rs) throws SQLException {
        rs.next();
        Car car = Car.builder().id(rs.getInt("Id"))
                .name(rs.getString("Name"))
                .color(rs.getString("Color"))
                .date(rs.getDate("Date"))
                .isAfterCrash(rs.getBoolean("isAfterCrash"))
                .build();
        return car;
    }
    /*   @Override
    public Car read(Integer id) throws SQLException {
        String sql = "SELECT * FROM CAR WHERE id = ?";
        Car Car;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            Car = createCarByResultSet(stm.executeQuery());
        }
        return Car;
    }
      */
}



