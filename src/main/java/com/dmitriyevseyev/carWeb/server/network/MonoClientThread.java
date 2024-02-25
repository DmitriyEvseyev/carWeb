package com.dmitriyevseyev.carWeb.server.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MonoClientThread extends Thread {
    private Socket clientSocket;
    private boolean exit;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ServerListener serverListener;
    private ServerSendler serverSendler;


    public MonoClientThread(Socket client) {
        this.clientSocket = client;
    }

    public ServerSendler getServerSendler() {
        return serverSendler;
    }

    public void disconnectClient() {
        serverListener.setExit(false);
        serverListener.interrupt();
        serverSendler.close();
        try {
            clientSocket.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void run() {
        System.out.println("MonoClientThread is running!");
        System.out.println(Thread.currentThread());
        this.exit = true;

        try {
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            serverSendler = new ServerSendler();
            serverSendler.setObjectOutputStream(objectOutputStream);

            serverListener = new ServerListener();
            serverListener.setObjectInputStream(objectInputStream);
            serverListener.start();

        } catch (IOException e) {
            System.out.println("serverStreamError. " + e.getMessage());
        }
    }
}
