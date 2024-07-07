package com.dmitriyevseyev.carWeb.server.dao.postgreSQL;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.DealerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.DeleteDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetAllDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.UpdateDealerException;
import org.hibernate.HibernateException;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PostgreSQLDealerDAO implements DealerDAO {
    private static PostgreSQLDealerDAO instance;
    private Connection connection;

    public static PostgreSQLDealerDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new PostgreSQLDealerDAO(connection);
        }
        return instance;
    }

    public PostgreSQLDealerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createDealer(CarDealership dealer) throws  AddDealerExeption {
        String sql = "INSERT INTO DEALERS (dealer_name, dealer_address)  VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, dealer.getName());
            stmt.setString(2, dealer.getAddress());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AddDealerExeption(String.format("AddDealerExeption: %s. Code: %s", e.getMessage()));
        }
    }

    @Override
    public CarDealership getDealer(Integer id) throws  NotFoundException {
        String sql = "SELECT * FROM DEALERS WHERE dealer_id = ?";
        CarDealership dealer = null;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                dealer = CarDealership.builder()
                        .id(rs.getInt("dealer_id"))
                        .name(rs.getString("dealer_name"))
                        .address(rs.getString("dealer_address"))
                        .build();
                return dealer;
            }
        } catch (SQLException e) {
            throw new NotFoundException(String.format("The dealer was not found!  %s.", e.getMessage()));
        }
        return dealer;
    }
    @Override
    public CarDealership getDealerByName (String name) throws  NotFoundException {
        String sql = "SELECT * FROM DEALERS WHERE dealer_name = ?";
        CarDealership dealer = null;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                dealer = CarDealership.builder()
                        .id(rs.getInt("dealer_id"))
                        .name(rs.getString("dealer_name"))
                        .address(rs.getString("dealer_address"))
                        .build();
                return dealer;
            }
        } catch (SQLException e) {
            throw new NotFoundException(String.format("The dealer was not found!  %s. ", e.getMessage()));
        }
        return dealer;
    }

    @Override
    public void update(CarDealership dealer) throws UpdateDealerException {
        String sql = "UPDATE DEALERS SET dealer_name = ?, dealer_address = ?  WHERE dealer_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setString(1, dealer.getName());
            stm.setString(2, dealer.getAddress());
            stm.setInt(3, dealer.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateDealerException(String.format("EditDealerExeption: %s.", e.getMessage()));
        }
    }

    @Override
    public void delete(Integer id) throws  DeleteDealerExeption {
        String sql = "DELETE FROM DEALERS WHERE dealer_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException e) {
            throw new DeleteDealerExeption(String.format("DeleteDealerExeption: %s.", e.getMessage()));
        }
    }

    @Override
    public List<CarDealership> getAll() throws GetAllDealerExeption {
        List<CarDealership> list;
        String sql = "SELECT * FROM DEALERS";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            list = createListByResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getSortedByCriteria: %s ", e.getMessage()));
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public List<CarDealership> getSortedByCriteria(String column, String criteria) throws  GetAllDealerExeption {
        List<CarDealership> list;
        String sql = "SELECT * FROM DEALERS ORDER BY %s %s";
        sql = String.format(sql, column, criteria);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            list = createListByResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getSortedByCriteria: %s ", e.getMessage()));
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public List<CarDealership> getFilteredByPattern(String column, String pattern, String criteria) throws GetAllDealerExeption {
        List<CarDealership> list;
        String sql = "SELECT * FROM DEALERS WHERE %s LIKE \'%s\' ORDER BY %s %s";
        sql = String.format(sql, column, pattern, column, criteria);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            list = createListByResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getSortedByCriteria: %s ", e.getMessage()));
        }
        return Collections.unmodifiableList(list);
    }


    private List<CarDealership> createListByResultSet(ResultSet rs) throws SQLException {
        List<CarDealership> list = new LinkedList<>();
        while (rs.next()) {
            list.add(CarDealership.builder()
                    .id(rs.getInt("dealer_id"))
                    .name(rs.getString("dealer_name"))
                    .address(rs.getString("dealer_address"))
                    .build());
        }
        return list;
    }
}