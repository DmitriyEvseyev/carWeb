package com.dmitriyevseyev.carWeb.server.dao.hibernate;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private SessionFactory sessionFactory;

    private static HibernateSessionFactoryUtil instance;

    public static HibernateSessionFactoryUtil getInstance() {
        if (instance == null) {
            instance = new HibernateSessionFactoryUtil();
        }
        return instance;
    }
    private HibernateSessionFactoryUtil() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Car.class);
        configuration.addAnnotatedClass(CarDealership.class);
        configuration.addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}