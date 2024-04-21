package com.dmitriyevseyev.carWeb.server.strategy;

public class StrategyNotFoundException extends Exception {
    public StrategyNotFoundException() {
    }

    public StrategyNotFoundException(String message) {
        super(message);
    }
}
