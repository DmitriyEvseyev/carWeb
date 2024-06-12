package com.dmitriyevseyev.carWeb.server.exceptions;

public class DAOFactoryActionException extends Throwable {
    public DAOFactoryActionException() {
        super();
    }

    public DAOFactoryActionException(String message) {
        super(message);
    }
}