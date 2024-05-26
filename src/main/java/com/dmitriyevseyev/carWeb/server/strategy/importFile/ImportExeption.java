package com.dmitriyevseyev.carWeb.server.strategy.importFile;

public class ImportExeption extends Exception {
    public ImportExeption(){
        super();
    }

    public ImportExeption(String message){
        super(message);
    }
}