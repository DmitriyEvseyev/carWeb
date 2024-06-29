package com.dmitriyevseyev.carWeb.server.dao.hibernate;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.DealerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.DeleteDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetAllDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.UpdateDealerException;
import org.hibernate.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HibernateDealerDAO implements DealerDAO {
    private static HibernateDealerDAO instance;
    private final SessionFactory sessionFactory;

    public static HibernateDealerDAO getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new HibernateDealerDAO(sessionFactory);
        }
        return instance;
    }
    public HibernateDealerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createDealer(CarDealership dealer) throws AddDealerExeption {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx1 = session.beginTransaction();
            session.persist(dealer);
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new AddDealerExeption(String.format("AddDealerExeption: %s. Code: %s", e.getMessage()));
        }
    }

    public CarDealership getDealer(Integer id) throws NotFoundException {
        try (Session session = sessionFactory.openSession()) {
            CarDealership dealer = session.find(CarDealership.class, id);
            return dealer;
        } catch (HibernateException e) {
            throw new NotFoundException(String.format("The dealer was not found!  %s.", e.getMessage()));
        }
    }

    public CarDealership getDealerByName(String name) throws NotFoundException {
        List<CarDealership> dealers = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            String hql = "From CarDealership where dealer_name = :dealer_name";
            Query query = session.createQuery(hql, CarDealership.class);
            query.setParameter("dealer_name", name);
            for (Object o : query.list()) {
                dealers.add((CarDealership) o);
            }
            return dealers.get(0);
        } catch (HibernateException e) {
            throw new NotFoundException(String.format("The dealer was not found!  %s. ", e.getMessage()));
        }
    }

    public void update(Integer id, String name, String address) throws UpdateDealerException {
        CarDealership dealer = CarDealership.builder().
                id(id).
                name(name).
                address(address).
                build();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(dealer);
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new UpdateDealerException(String.format("EditDealerExeption: %s.", e.getMessage()));
        }
    }

    public void delete(Integer id) throws DeleteDealerExeption {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            CarDealership dealer = session.find(CarDealership.class, id);
            session.remove(dealer);
        } catch (HibernateException e) {
            throw new DeleteDealerExeption(String.format("DeleteDealerExeption: %s.", e.getMessage()));
        }
    }

    public List<CarDealership> getAll() throws GetAllDealerExeption {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From CarDealership").getResultList();
        } catch (HibernateException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getSortedByCriteria: %s ", e.getMessage()));
        }
    }

    public List<CarDealership> getSortedByCriteria(String column, String criteria) throws GetAllDealerExeption {
        List<CarDealership> dealers = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            String hql = "From CarDealership " + "ORDER BY %s %s";
            hql = String.format(hql, column, criteria);
            Query query = session.createQuery(hql);
            for (Object o : query.list()) {
                dealers.add((CarDealership) o);
            }
            return dealers;
        } catch (HibernateException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getSortedByCriteria: %s ", e.getMessage()));
        }
    }

    public List<CarDealership> getFilteredByPattern(String column, String pattern, String criteria) throws GetAllDealerExeption {
        List<CarDealership> dealers = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            String hql = "From CarDealership where (%s LIKE :pattern) " + "ORDER BY %s %s";
            hql = String.format(hql, column, column, criteria);
            Query query = session.createQuery(hql);
            query.setParameter("pattern", pattern);
            for (Object o : query.list()) {
                dealers.add((CarDealership) o);
            }
            return dealers;
        } catch (HibernateException e) {
            throw new GetAllDealerExeption(String.format("GetAllDealerExeption, getSortedByCriteria: %s ", e.getMessage()));
        }
    }
}
