package com.dmitriyevseyev.carWeb.server.network.handlers;

import com.dmitriyevseyev.carWeb.server.network.MonoClientThread;
import com.dmitriyevseyev.carWeb.server.network.ServerFacade;
import com.dmitriyevseyev.carWeb.shared.model.Command;
import com.dmitriyevseyev.carWeb.shared.model.CommandId;

public class DisconnectServerHandler implements ServerHandler {
    @Override
    public void handle(Command command) {
        Integer idUser = (Integer) command.getData();
        MonoClientThread monoClient = ServerFacade.getInstance().getThreadHashMap().get(idUser);
        Command com = new Command(CommandId.DISCONNECT, "");

        monoClient.getServerSendler().send(com);
        monoClient.disconnectClient();

        ServerFacade.getInstance().getThreadHashMap().remove(idUser);
    }
}
