package com.dmitriyevseyev.carWeb.server.network.handlers;

import com.dmitriyevseyev.carWeb.server.controller.ServerCarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.DeleteCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.network.MonoClientThread;
import com.dmitriyevseyev.carWeb.server.network.ServerCommandManager;
import com.dmitriyevseyev.carWeb.server.network.ServerFacade;
import com.dmitriyevseyev.carWeb.server.network.ServerSendler;
import com.dmitriyevseyev.carWeb.shared.model.Command;
import com.dmitriyevseyev.carWeb.shared.model.CommandId;

import java.util.HashMap;

public class DeleteCarServerHandler implements ServerHandler {
    @Override
    public void handle(Command command) {

        Integer id = (Integer) command.getData();
        System.out.println("It will be deleted ID - " + id);
        try {
            ServerCarController.getInstance().removeCar(id);
        } catch (NotFoundException e) {
            System.out.println("There is no this car. " + e.getMessage());
        } catch (DeleteCarExeption e) {
            Command error = new Command(CommandId.ERROR,"error DeleteCar,  " + e.getMessage());
            HashMap<Integer, MonoClientThread> map = ServerFacade.getInstance().getThreadHashMap();
            for (MonoClientThread monoClient : map.values()) {
                monoClient.getServerSendler().send(error);
            }
            System.out.println("DeleteCarError. " + e.getMessage());
        }

        ServerCommandManager.getInstance().processCommand(new Command(CommandId.SEND_ALL, ""));

        /*
        try {
            Command com = new Command(CommandId.GET_ALL_CARS, ServerCarController.getInstance().getAllCars());
            System.out.println("Com responce (GetAllCarsServerHandler/DeleteCarServerHandler) - " + com + "\n");
            ServerSendler.getInstance().send(com);
        } catch (GetAllCarExeption e) {
            Command error = new Command(CommandId.ERROR, "error GetAllCars,  " + e.getMessage());
            ServerSendler.getInstance().send(error);
            System.out.println(e.getMessage());
        }
         */
    }
}
