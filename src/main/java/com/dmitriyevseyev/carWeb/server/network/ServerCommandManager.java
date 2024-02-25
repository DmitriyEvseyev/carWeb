package com.dmitriyevseyev.carWeb.server.network;

import com.dmitriyevseyev.carWeb.server.network.handlers.*;

import com.dmitriyevseyev.carWeb.shared.model.Command;
import com.dmitriyevseyev.carWeb.shared.model.CommandId;

import java.util.HashMap;
import java.util.Map;

public class ServerCommandManager {
    private static ServerCommandManager instance;

    public static ServerCommandManager getInstance() {
        if (instance == null) {
            instance = new ServerCommandManager();
        }
        return instance;
    }

    private Map<Integer, ServerHandler> handlerMap;

    private ServerCommandManager() {
        handlerMap = new HashMap<>();
        handlerMap.put(CommandId.GET_ALL_CARS, new GetAllCarsServerHandler());
        handlerMap.put(CommandId.ADD_CAR, new AddCarServerHandler());
        handlerMap.put(CommandId.DELETE_CAR, new DeleteCarServerHandler());
        handlerMap.put(CommandId.UPDATE_CAR, new UpdateCarServerHandler());
        handlerMap.put(CommandId.DISCONNECT, new DisconnectServerHandler());
        handlerMap.put(CommandId.AUTHORIZATION, new AuthorizationServerHandler());
        handlerMap.put(CommandId.NEW_USER, new NewUserServerHandler());
        handlerMap.put(CommandId.SEND_ALL, new SendAllServerHandler());
    }

    public void processCommand(Command command) {
        handlerMap.get(command.getAction()).handle(command);
    }
}

