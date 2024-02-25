package com.dmitriyevseyev.carWeb.server.network.handlers;

import com.dmitriyevseyev.carWeb.shared.model.Command;

public interface ServerHandler {
    void  handle (Command command);
}
