package com.dmitriyevseyev.carWeb.server.network;

import com.dmitriyevseyev.carWeb.shared.model.Command;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerListener extends Thread {
    private ObjectInputStream objectInputStream;
    private boolean exit;

    public ServerListener() {
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public void run() {
        System.out.println("ServerListener is running!");
        exit = true;
        try {
            while (exit) {
                Thread.sleep(5000);
               // if (objectInputStream.available() > 0) {
                    Command request = (Command) objectInputStream.readObject();
                    System.out.println("request - " + request);
                    ServerCommandManager.getInstance().processCommand(request);
                }
            System.out.println("ServerListener closed.");
          //  }
        } catch (InterruptedException ignored) {
        } catch (IOException e) {
            System.out.println("ReadObjectServerError. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundExceptionClassNotFoundExceptionServerError. " + e.getMessage());
        }
    }
}


