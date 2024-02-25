package com.dmitriyevseyev.carWeb.server.network;

import com.dmitriyevseyev.carWeb.shared.model.Command;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerSendler {
    private ObjectOutputStream objectOutputStream;
    private static ServerSendler instance;

    public static ServerSendler getInstance() {
        if (instance == null) {
            instance = new ServerSendler();
        }
        return instance;
    }

    public ServerSendler() {
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public void send (Command command) {
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();

        } catch (IOException e) {
            System.out.println("ServerSendlerError. " + e.getMessage());
        }
    }

    public void close () {
        try {
            System.out.println("ServerSendler closed.");
            objectOutputStream.close();

        } catch (IOException e) {
            System.out.println("ServerSendlerError/ objectOutputStreamClose. " + e.getMessage());
        }
    }
}

