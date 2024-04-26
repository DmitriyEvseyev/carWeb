package com.dmitriyevseyev.carWeb.server.dao;

import com.dmitriyevseyev.carWeb.model.Car;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class CarDAO implements DAO {
    private static CarDAO instance;
    private Connection connection;

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
        String sql = "INSERT INTO CAR (IDDEALER, NAME, DATE, COLOR, ISAFTERCRASH)  VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, car.getIdDealer());
            stmt.setString(2, car.getName());
            stmt.setDate(3, (new java.sql.Date(car.getDate().getTime())));
            stmt.setString(4, car.getColor());
            stmt.setBoolean(5, car.isAfterCrash());
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

    public Car getCar(Integer id) throws SQLException {
        Car car = null;
        String sql = "SELECT * FROM CAR WHERE ID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                car = Car.builder()
                        .id(rs.getInt("Id"))
                        .idDealer(rs.getInt("IdDealer"))
                        .name(rs.getString("Name"))
                        .color(rs.getString("Color"))
                        .date(rs.getDate("Date"))
                        .isAfterCrash(rs.getBoolean("isAfterCrash"))
                        .build();
            }
        }
        return car;
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

    public List<Car> getSortedByCriteria(Integer IdDealer, String column, String criteria) throws SQLException {
        List<Car> list;
        String sql = "SELECT * FROM CAR WHERE idDealer = ? ORDER BY %s %s";

               sql = String.format(sql, column, criteria);


        System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, IdDealer);
            list = createListByResultSet(statement.executeQuery());
        }
        return Collections.unmodifiableList(list);
    }

    public List<Car> getFilteredByPattern(Integer IdDealer, String column, String pattern, String criteria) throws SQLException {
        List<Car> list;
        String sql = "SELECT * FROM CAR WHERE idDealer = ? AND %s LIKE \'%s\' ORDER BY %s %s";
        sql = String.format(sql, column, pattern, column, criteria);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, IdDealer);
            list = createListByResultSet(statement.executeQuery());
        }
        return Collections.unmodifiableList(list);
    }

    public List<Car> getFilteredByDatePattern(Integer IdDealer, String columnDate, Date startDatePattern, Date endDatePattern, String criteria) throws SQLException {
        List<Car> list;
        String sql = "SELECT * FROM CAR WHERE idDealer = ? AND %s BETWEEN \'%s\' AND  \'%s\' ORDER BY %s %s";
        Date beginDate = new java.sql.Date(startDatePattern.getTime());
        Date endDate = new java.sql.Date(endDatePattern.getTime());
        sql = String.format(sql, columnDate, beginDate, endDate, columnDate, criteria);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, IdDealer);
            list = createListByResultSet(statement.executeQuery());
        }
        return Collections.unmodifiableList(list);
    }

    public List<Car> getFilteredByCrashPattern(Integer IdDealer, String column, String pattern, String criteria) throws SQLException {
        List<Car> list;
        String sql = "SELECT * FROM CAR WHERE idDealer = ? AND isAfterCrash = ? ORDER BY %s %s";
        sql = String.format(sql,  column, criteria);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, IdDealer);
            statement.setBoolean(2, Boolean.parseBoolean(pattern));
            list = createListByResultSet(statement.executeQuery());
        }
        return Collections.unmodifiableList(list);
    }
}



