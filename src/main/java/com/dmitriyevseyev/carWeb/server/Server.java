package com.dmitriyevseyev.carWeb.server;

import com.dmitriyevseyev.carWeb.server.dao.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.network.ServerFacade;

public class Server {
    public static void main(String[] args) {
        ManagerDAO.getInstance();
        ServerFacade.getInstance().connect();
    }
}
