package com.dmitriyevseyev.carWeb.server.dao.hibernate;

import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.UserDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.user.AddUserExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserNotFoundExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserPasswordExeption;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class HibernateUserDAO implements UserDAO {
    private static HibernateUserDAO instance;
    private final SessionFactory sessionFactory;

    public HibernateUserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static HibernateUserDAO getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new HibernateUserDAO(sessionFactory);
        }
        return instance;
    }

    @Override
    public void createUser(User user) throws AddUserExeption {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
            session.close();
        } catch (HibernateException e) {
            throw new AddUserExeption(String.format("AddUserExeption. Error: %s. Code: %s", e.getMessage()));
        }
    }

    @Override
    public boolean isUserExist(String userName) throws UserNotFoundExeption {
        boolean userExist;
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            String hql = "From User where user_name = :user_name";
            Query query = session.createQuery(hql, User.class);
            query.setParameter("user_name", userName);
            for (Object o : query.list()) {
                users.add((User) o);
            }

            System.out.println("users.get(0) - " + users.get(0));


            if (users.get(0) != null) {
                userExist = true;
            } else {
                userExist = false;
            }
        } catch (HibernateException e) {
            throw new UserNotFoundExeption("Error! User has not been found.");
        }
        return userExist;
    }

    @Override
    public String getPassword(String userName) throws UserPasswordExeption, UserNotFoundExeption {
        List<String> pass = new ArrayList<>();
        String password = null;
        if (!isUserExist(userName)) {
            return password;
        } else {
            String hql = "Select user.password From User user where user_name = :user_name";
            try (Session session = sessionFactory.openSession()) {
                Query query = session.createQuery(hql);
                query.setParameter("user_name", userName);
                for (Object o : query.list()) {
                    pass.add((String) o);
                }

                System.out.println("password - " + pass.get(0));


                password = pass.get(0);
            } catch (HibernateException e) {
                throw new UserPasswordExeption("Password error! ");
            }
        }
        return password;
    }
}

