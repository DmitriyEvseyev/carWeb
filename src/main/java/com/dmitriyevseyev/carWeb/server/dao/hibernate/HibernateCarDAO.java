package com.dmitriyevseyev.carWeb.server.dao.hibernate;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.CarDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.*;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HibernateCarDAO implements CarDAO {
    private static HibernateCarDAO instance;
    private final SessionFactory sessionFactory;

    public HibernateCarDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static HibernateCarDAO getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new HibernateCarDAO(sessionFactory);
        }
        return instance;
    }

    @Override
    public void createCar(Car car) throws AddCarExeption {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(car);
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new AddCarExeption(String.format("AddCarExeption. Error: %s. Code: %s", e.getMessage()));
        }
    }

    @Override
    public Car getCar(Integer id) throws NotFoundException {
        try (Session session = sessionFactory.openSession()) {
            Car car = session.find(Car.class, id);
            return car;
        } catch (HibernateException e) {
            throw new NotFoundException(String.format("The car was not found!  %s.", e.getMessage()));
        }
    }

    @Override
    public List<Car> getCarListDealer(Integer idDealer) throws GetAllCarExeption {
        try (Session session = sessionFactory.openSession()) {
            List<Car> cars = new ArrayList<>();
            String hql = "From Car where dealer_id = :dealer_id";
            Query query = session.createQuery(hql);
            query.setParameter("dealer_id", idDealer);
            for (Object o : query.list()) {
                cars.add((Car) o);
            }
            return cars;
        } catch (HibernateException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption. Error: %s.", e.getMessage()));
        }
    }

    @Override
    public void update(Car car) throws UpdateCarException {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(car);
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new UpdateCarException(String.format("UpdateCarException: %s.", e.getMessage()));
        }
    }
    @Override
    public void delete(Integer id) throws DeleteCarExeption {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            Car delCar = session.find(Car.class, id);
            session.remove(delCar);
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new DeleteCarExeption(String.format("DeleteCarExeption: %s.", e.getMessage()));
        }
    }


    @Override
    public List<Car> getSortedByCriteria(Integer idDealer, String column, String criteria) throws GetAllCarExeption {
        List<Car> cars = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            String hql = "From Car where dealer_id = :dealer_id " + "ORDER BY %s %s";
            hql = String.format(hql, column, criteria);
            Query query = session.createQuery(hql);
            query.setParameter("dealer_id", idDealer);
            for (Object o : query.list()) {
                cars.add((Car) o);
            }
            return cars;
        } catch (HibernateException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption, getSortedByCriteria: %s" + e.getMessage()));
        }
    }

    @Override
    public List<Car> getFilteredByPattern(Integer idDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
        List<Car> cars = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            String hql = "From Car where dealer_id = :dealer_id and (%s LIKE :pattern) " + "ORDER BY %s %s";
            hql = String.format(hql, column, column, criteria);
            Query query = session.createQuery(hql);
            query.setParameter("dealer_id", idDealer);
            query.setParameter("pattern", pattern);
            for (Object o : query.list()) {
                cars.add((Car) o);
            }
            return cars;
        } catch (HibernateException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption, getSortedByCriteria: %s" + e.getMessage()));
        }
    }

    @Override
    public List<Car> getFilteredByDatePattern(Integer idDealer, String columnDate, Date startDatePattern, Date endDatePattern, String criteria) throws GetAllCarExeption {
        List<Car> cars = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            String hql = "From Car where dealer_id = :dealer_id and (%s BETWEEN :startDatePattern and :endDatePattern) " + "ORDER BY %s %s";
            hql = String.format(hql, columnDate, columnDate, criteria);
            Query query = session.createQuery(hql);
            query.setParameter("dealer_id", idDealer);
            query.setParameter("startDatePattern", startDatePattern);
            query.setParameter("endDatePattern", endDatePattern);
            for (Object o : query.list()) {
                cars.add((Car) o);
            }
            return cars;
        } catch (HibernateException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption, getFilteredByDatePattern: %s" + e.getMessage()));
        }
    }

    @Override
    public List<Car> getFilteredByCrashPattern(Integer idDealer, String column, String pattern, String criteria) throws GetAllCarExeption {
        List<Car> cars = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            String hql = "From Car where dealer_id = :dealer_id and isAfterCrash = :pattern " + "ORDER BY %s %s";
            hql = String.format(hql, column, criteria);
            Query query = session.createQuery(hql);
            query.setParameter("dealer_id", idDealer);
            query.setParameter("pattern", Boolean.parseBoolean(pattern));
            for (Object o : query.list()) {
                cars.add((Car) o);
            }
            return cars;
        } catch (HibernateException e) {
            throw new GetAllCarExeption(String.format("GetAllCarExeption, getFilteredByCrashPattern: %s" + e.getMessage()));
        }
    }
}

