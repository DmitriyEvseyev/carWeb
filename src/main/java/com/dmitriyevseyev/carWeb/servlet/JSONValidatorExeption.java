package com.dmitriyevseyev.carWeb.servlet;

public class JSONValidatorExeption extends Exception {
    public JSONValidatorExeption() {
    }

    public JSONValidatorExeption (String message) {
        super(message);
    }
}
