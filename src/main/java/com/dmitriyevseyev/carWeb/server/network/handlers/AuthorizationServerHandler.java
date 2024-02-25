package com.dmitriyevseyev.carWeb.server.network.handlers;

import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.network.MonoClientThread;
import com.dmitriyevseyev.carWeb.server.network.ServerCommandManager;
import com.dmitriyevseyev.carWeb.server.network.ServerFacade;
import com.dmitriyevseyev.carWeb.server.network.ServerSendler;
import com.dmitriyevseyev.carWeb.server.controller.ServerCarController;
import com.dmitriyevseyev.carWeb.server.controller.ServerUserController;
import com.dmitriyevseyev.carWeb.shared.model.Command;
import com.dmitriyevseyev.carWeb.shared.model.CommandId;
import com.dmitriyevseyev.carWeb.model.User;

import java.util.HashMap;

public class AuthorizationServerHandler implements ServerHandler {
    @Override
    public void handle(Command command) {
        User user = (User) command.getData();
        ServerUserController serverUserController = ServerUserController.getInstance();
        ServerFacade serverFacade = ServerFacade.getInstance();

        boolean isCorrect = serverUserController.isUserExistServer(user);
        System.out.println("boolean - " + isCorrect);

        if (isCorrect) {
            Integer idUser = serverUserController.getIdUser(user);
            System.out.println("idUser - " + idUser);
            HashMap<Integer, MonoClientThread> map = serverFacade.getThreadHashMap();

            map.put(idUser, serverFacade.getMonoClientThread());
            System.out.println("threadHashMap - " + map.keySet() + ", " + map.values());

            ServerCommandManager.getInstance().processCommand(new Command(CommandId.GET_ALL_CARS, idUser));

            Command idCom = new Command(CommandId.GET_USER_ID, idUser);
            System.out.println("Com responce, idUser - " + idCom);
            map.get(idUser).getServerSendler().send(idCom);

        } else {
            Command comUser = new Command(CommandId.REGISTER_NEW_USER, "");
            System.out.println("New user. " + comUser);
            ServerSendler.getInstance().send(comUser);
        }
    }
}
