package com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption;

public class CarIdAlreadyExistException extends Exception {
    public CarIdAlreadyExistException(){
        super();
    }

    public CarIdAlreadyExistException(String message){
        super(message);
    }
}