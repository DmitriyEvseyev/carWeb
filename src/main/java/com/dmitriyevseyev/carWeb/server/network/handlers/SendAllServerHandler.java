package com.dmitriyevseyev.carWeb.server.network.handlers;

import com.dmitriyevseyev.carWeb.server.controller.ServerCarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.network.MonoClientThread;
import com.dmitriyevseyev.carWeb.server.network.ServerCommandManager;
import com.dmitriyevseyev.carWeb.server.network.ServerFacade;
import com.dmitriyevseyev.carWeb.server.network.ServerSendler;
import com.dmitriyevseyev.carWeb.shared.model.Command;
import com.dmitriyevseyev.carWeb.shared.model.CommandId;

import java.util.HashMap;

public class SendAllServerHandler implements ServerHandler {
    @Override
    public void handle(Command command) {
        HashMap<Integer, MonoClientThread> map = ServerFacade.getInstance().getThreadHashMap();
        Command com = null;
        try {
            com = new Command(CommandId.GET_ALL_CARS, ServerCarController.getInstance().getAllCars());
            System.out.println("Com responce (SendAllServerHandler, GetAllCars) - " + com + "\n");

        } catch (GetAllCarExeption e) {
            Command error = new Command(CommandId.ERROR, "error GetAllCars,  SendAllServerHandler. " + e.getMessage());
            for (MonoClientThread monoClient : map.values()) {
                monoClient.getServerSendler().send(error);
            }
            System.out.println("GetAllCarExeption, SendAllServerHandler. " + e.getMessage());
        }

        for (MonoClientThread monoClient : map.values()) {
            monoClient.getServerSendler().send(com);
        }
    }
}

