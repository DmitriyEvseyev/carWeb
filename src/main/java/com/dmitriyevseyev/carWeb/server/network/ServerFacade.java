package com.dmitriyevseyev.carWeb.server.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.dmitriyevseyev.carWeb.shared.utils.Constants.SERVER_PORT;

public class ServerFacade {
    private static ServerFacade instance;
    private static Socket clientSocket;
    private static ServerSocket server;
    private HashMap<Integer, MonoClientThread> threadHashMap;
    private ExecutorService executorService;
    private  MonoClientThread monoClientThread;

    public static ServerFacade getInstance() {
        if (instance == null) {
            instance = new ServerFacade();
        }
        return instance;
    }

    private ServerFacade() {
        threadHashMap = new HashMap<>();
        executorService = Executors.newFixedThreadPool(7);
    }

    public synchronized HashMap<Integer, MonoClientThread> getThreadHashMap() {
        return threadHashMap;
    }

    public MonoClientThread getMonoClientThread() {
        return monoClientThread;
    }

   public void connect() {
        try {
            server = new ServerSocket(Integer.parseInt(SERVER_PORT));
            System.out.println("The server is running!");

        } catch (IOException e) {
            System.out.println("serverSocketRunError. " + e.getMessage());
        }
        while (true) {
            try {
                clientSocket = server.accept();
                System.out.println("client connected");
                System.out.println("MAIN - " + Thread.currentThread());
            } catch (IOException e) {
                System.out.println("clientConnectedError. " + e.getMessage());
            }

            monoClientThread = new MonoClientThread(clientSocket);
            System.out.println("monoClientThread - " + monoClientThread);
            try {
                executorService.execute(monoClientThread);
            } catch (Exception e ) {
                System.out.println("executorService.execute(monoClientThread) --- " + e.getMessage());
            }
            System.out.println("monoClientThread  facade - " + monoClientThread);
          // executorService.shutdown();
            /*new Thread(() -> {
                MonoClientThread thread = new MonoClientThread(clientSocket);
                executorService.execute(thread);
                threadHashMap.put(idUser, thread);
                System.out.println("threadHashMap - " + threadHashMap.keySet() + ", " + threadHashMap.values());
             */


                /*try {
                    this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                } catch (IOException e) {
                    System.out.println("serverStreamError. " + e.getMessage());
                }
                serverSendler = ServerSendler.getInstance();
                serverSendler.setObjectOutputStream(objectOutputStream);

                serverListener = new ServerListener();
                serverListener.setObjectInputStream(objectInputStream);
                serverListener.start();

                 */

        }
    }
}
