package com.dmitriyevseyev.carWeb.server.exceptions.car;

public class UpdateCarException extends Exception {

    public UpdateCarException() {
        super();
    }

    public UpdateCarException (String message) {
        super(message);
    }
}
