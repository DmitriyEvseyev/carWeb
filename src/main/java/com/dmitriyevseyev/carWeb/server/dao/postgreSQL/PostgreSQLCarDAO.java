package com.dmitriyevseyev.carWeb.server.dao.postgreSQL;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.CarDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class PostgreSQLCarDAO implements CarDAO {
    private static PostgreSQLCarDAO instance;
    private Connection connection;

    public static PostgreSQLCarDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new PostgreSQLCarDAO(connection);
        }
        return instance;
    }

    public PostgreSQLCarDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createCar(Car car) throws AddCarExeption {
        String sql = "INSERT INTO CARS (dealer_id, car_name, car_date, car_color, is_after_crash)  VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, car.getIdDealer());
            stmt.setString(2, car.getName());
            stmt.setDate(3, (new java.sql.Date(car.getDate().getTime())));
            stmt.setString(4, car.getColor());
            stmt.setBoolean(5, car.isAfterCrash());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AddCarExeption(String.format("AddCarExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    @Override
    public List<Car> getCarListDealer(Integer idDealer) throws GetAllCarExeption {
        String sql = "SELECT * FROM CARS WHERE dealer_id = ?";
        List<Car> list = null;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, idDealer);
            list = createListByResultSet(stm.executeQuery());
        } catch (SQLException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption. Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public Car getCar(Integer id) throws NotFoundException {
        Car car = null;
        String sql = "SELECT * FROM CARS WHERE car_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                car = Car.builder()
                        .id(rs.getInt("car_id"))
                        .idDealer(rs.getInt("dealer_id"))
                        .name(rs.getString("car_name"))
                        .date(rs.getDate("car_date"))
                        .color(rs.getString("car_color"))
                        .isAfterCrash(rs.getBoolean("is_after_crash"))
                        .build();
            }
        } catch (SQLException e) {
            throw new NotFoundException(String.format("The car was not found!  %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
        return car;
    }

    @Override
    public void update(Car car) throws UpdateCarException {
        String sql = "UPDATE CARS SET car_name = ?, car_date = ?, car_color = ?, is_after_crash = ?  WHERE car_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setString(1, car.getName());
            stm.setDate(2, (new java.sql.Date(car.getDate().getTime())));
            stm.setString(3, car.getColor());
            stm.setBoolean(4, car.isAfterCrash());
            stm.setInt(5, car.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateCarException(String.format("UpdateCarException: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    @Override
    public void delete(Integer id) throws DeleteCarExeption {
        String sql = "DELETE FROM CARS WHERE car_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException e) {
            throw new DeleteCarExeption(String.format("DeleteCarExeption: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
    }

    public List<Car> createListByResultSet(ResultSet rs) throws GetAllCarExeption {
        List<Car> list = new LinkedList<>();
        try {
            while (rs.next()) {
                list.add(Car.builder()
                        .id(rs.getInt("car_id"))
                        .idDealer(rs.getInt("dealer_id"))
                        .name(rs.getString("car_name"))
                        .date(rs.getDate("car_date"))
                        .color(rs.getString("car_color"))
                        .isAfterCrash(rs.getBoolean("is_after_crash"))
                        .build());
            }
        } catch (SQLException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption. Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
        return list;
    }

    @Override
    public List<Car> getSortedByCriteria(Integer IdDealer, String column, String criteria) throws GetAllCarExeption {
        List<Car> list;
        String sql = "SELECT * FROM CARS WHERE dealer_id = ? ORDER BY %s %s";

        sql = String.format(sql, column, criteria);

        System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, IdDealer);
            list = createListByResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption. Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Car> getFilteredByPattern(Integer IdDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
        List<Car> list;
        String sql = "SELECT * FROM CARS WHERE dealer_id = ? AND %s LIKE \'%s\' ORDER BY %s %s";
        sql = String.format(sql, column, pattern, column, criteria);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, IdDealer);
            list = createListByResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption. Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Car> getFilteredByDatePattern(Integer IdDealer, String columnDate, Date startDatePattern, Date endDatePattern, String criteria) throws GetAllCarExeption {
        List<Car> list;
        String sql = "SELECT * FROM CARS WHERE dealer_id = ? AND %s BETWEEN \'%s\' AND  \'%s\' ORDER BY %s %s";
        Date beginDate = new java.sql.Date(startDatePattern.getTime());
        Date endDate = new java.sql.Date(endDatePattern.getTime());
        sql = String.format(sql, columnDate, beginDate, endDate, columnDate, criteria);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, IdDealer);
            list = createListByResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption. Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Car> getFilteredByCrashPattern(Integer IdDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
        List<Car> list;
        String sql = "SELECT * FROM CARS WHERE dealer_id = ? AND is_after_crash = ? ORDER BY %s %s";
        sql = String.format(sql, column, criteria);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, IdDealer);
            statement.setBoolean(2, Boolean.parseBoolean(pattern));
            list = createListByResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption. Error: %s. Code: %s", e.getMessage(), e.getSQLState()));
        }
        return Collections.unmodifiableList(list);
    }
}



