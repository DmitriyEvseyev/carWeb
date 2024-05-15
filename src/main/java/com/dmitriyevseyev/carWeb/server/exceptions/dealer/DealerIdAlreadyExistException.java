package com.dmitriyevseyev.carWeb.server.exceptions.dealer;

public class DealerIdAlreadyExistException extends Exception {
    public DealerIdAlreadyExistException() {
        super();
    }

    public DealerIdAlreadyExistException(String message) {
        super(message);
    }
}