package com.dmitriyevseyev.carWeb.server.strategy.importFile;

public interface ImportStrategy <T> {
    void store(T object);
}