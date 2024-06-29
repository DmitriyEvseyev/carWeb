package com.dmitriyevseyev.carWeb.server.dao.interfaces;

import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.server.exceptions.user.AddUserExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserNotFoundExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserPasswordExeption;

public interface UserDAO {
    void createUser(User user) throws AddUserExeption;

    boolean isUserExist(String userName) throws UserNotFoundExeption;

    String getPassword(String userName) throws UserPasswordExeption, UserNotFoundExeption;
}
